import React from 'react';

const style = {
  wrapper: {
    backgroundColor: '#ffffff',
    padding: '1px',
  },
  author: {
    fontSize: '1em',
    padding: 0,
    margin: '5px 0 0 0'
  },
  message: {
    fontSize: '1em',
    padding: 0,
    margin: '0 0 0 5px'
  }
};

const Chat = ({ message }) => (
  <div className="chat-box" style={style.wrapper}>
    <div className="chat-message" style={style.message}>
      <p>
        {message.author}: {message.entry}
      </p>
    </div>
  </div>
);

export default React.memo(Chat);
