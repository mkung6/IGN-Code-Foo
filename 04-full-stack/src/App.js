import React, { Component } from 'react';
import { graphql, compose } from 'react-apollo';
import gql from 'graphql-tag';

import './App.css';
import Chat from './components/Chat';

const style = {
  wrapper: {
    backgroundColor: '#b7b7b7',
    margin: '0 auto',
    padding: '5 5 5 5',
    width: '100%',
    maxWidth: '960px'
  },
  chatWrapper: {
    padding: 5,
    overflow: 'auto',
  },
  chatTitleWrapper: {
    backgroundColor: '#3553ff',
    border: '1px solid #5887a7',
    padding: '1px 0px 1px 5px'
  },
  chatTitle: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    color: '#fff',
    fontSize: '1em',
    fontFamily: 'Arial, Helvetica, sans-serif',
    fontWeight: 'bold',
    margin: 0,
    padding: 0,
    width: '100%'
  },
  messageInputWrapper: {
    backgroundColor: '#b7b7b7',
    margin: '5px',
    padding: 1,
    overflow: 'auto'
  },
  messageInput: {
    border: '0 solid #FFFFFF',
    borderTopWidth: 1,
    width: '100%',
  }
};

class App extends Component {
  state = {
    author: '',
    entry: ''
  }

  componentDidMount() {
    //get username from a window prompt
    const author = window.prompt("Enter a username");
    author && this.setState({ author });
    this.subscribeToNewMessages();
  }

  //create our graphql subscription, sending message on pressing Enter key
  subscribeToNewMessages = () => {
    this.props.allMessagesQuery.subscribeToMore({
      document: gql`
        subscription MessageSentSubscription {
          messageSent {
            id
            author
            entry
          }
        }
      `,
      updateQuery: (previousData, { subscriptionData }) => {
        return {
          messages: [
            ...previousData.messages,
            subscriptionData.data.messageSent
          ]
        };
      }
    });
  }
  createMessage = async e => {
    if (e.key === 'Enter') {
      const { author, entry } = this.state;
      await this.props.createMessageMutation({
        variables: { author, entry }
      });
      this.setState({ entry: '' });
    }
  }

  render() {
    const allMessages = this.props.allMessagesQuery.messages || [];
    return (
      <div className="chatApp" style={style.wrapper}>
        <div className="chatTitle" style={style.chatTitleWrapper}>
          <p style={style.chatTitle}>
            Instant Message
          </p>
        </div>
        <div className="chatWrapper" style={style.chatWrapper}>
          {allMessages.map(message => (
              <Chat
                key={message.id}
                message={message}
              />
          ))}
        </div>
        <div className="messageInputWrapper" style={style.chatInput}>
          <input
            value={this.state.entry}
            type="text"
            placeholder="[ start typing ]"
            style={style.messageInput}
            onChange={e => this.setState({ entry: e.target.value })}
            onKeyPress={this.createMessage}
          />
        </div>
      </div>
    );
  }
}

const ALL_MESSAGES_QUERY = gql`
  query AllMessagesQuery {
    messages {
      id
      author
      entry
    }
  }
`;

const CREATE_MESSAGE_MUTATION = gql`
  mutation CreateMessageMutation($author: String!, $entry: String!) {
    createMessage(author: $author, entry: $entry) {
      author
      entry
    }
  }
`;

export default compose(
  graphql(ALL_MESSAGES_QUERY, { name: 'allMessagesQuery' }),
  graphql(CREATE_MESSAGE_MUTATION, { name: 'createMessageMutation' })
)(App);
