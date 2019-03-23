## The problem

Given his funds, the Witcher Geralt of Rivia needs to purchase an armor set with the highest possible total armor value at an armorer.

Create a program that will determine the armor set of the highest value based on Geralt's available currency (Crowns).

Constraints:
* 300 Crowns available
* You must be able to afford the total price of your armor set
* Each piece of armor contains a type, name, price, and armor value
* An armor set requires one piece of armor of each type (Chest, Leggings, Helmet, Boots) as well as an extra piece that can be of any type
* Inventory in Armorer's shop provided
* Display the final armor set, including data

## Algorithm details

I'm a greedy person, and I want as much armor as I can get for as cheap as I can get it and do it fast. This problem reminds me of the knapsack problem, and no one wants to sit there and meticulously search through every possible combination. That could take a long time! How boring! The Witcher Geralt of Rivia has wyverns to fight. The real kicker is that in order to have a full set, I need an extra piece of any armor type. So the amount of total combinations would take far too long to look through if I try to find every single one!

Instead, first what I'll do is prioritize each armor based on its armor value to cost ratio. I do this by dividing the armor value by its cost, and then picking the highest of that ratio for each type. That way I can try to buy the most amount of armor while spending the least amount of Crowns. Simply picking the highest armor value here isn't smart and will get me into money trouble.

And here's where the details get juicy. I've already selected my four main armor types. All I have to do now is see how many Crowns I have left, and buy the armor with the highest armor value for my extra piece! I can be as greedy as I can get here!

Now, I just have to make sure I didn't over-spend. If I did, then I just find the next highest armor to cost ratio and swap it with what I already picked. Then I re-select the highest armor value that I can afford as my extra piece.

Once I think I have the best armor set that I can buy, I show the results of what I want to purchase and be on my merry way.

## Implementation details

Please download the java files and txt file to a local folder. Compile `CalculateArmorSet.java` and run.

First, we read in the Armorer's inventory, which is currently stored in `inventory.txt`. We take each armor piece and store it in a max heap, or a Java `PriorityQueue`, and separate the pieces by armor type. I use a heap so that insertion is O(logn) and keeps the queue ordered. Peeking at the max is a constant operation, and taking it is also O(logn) as the heap re-orders itself to maintain the priority.

I take advantage of the way that Java is object-oriented and create my own custom Armor and Witcher class. This allows me to use built in data types and operations to store and manipulate information.

I broke up code and made helper functions to try and repeat myself as little as possible. I also implemented each function with the goal of getting it as close to linear time and O(n) space as I could--although I'm using auxiliary and temporary data structures to store information. However, my `quePointer` is only of size 4, and `aux` is bound to the size of the input but is proportional to my `PriorityQueue` sizes. I say as close to linear time as possible, because I tried to avoid any O(nlogn) operations with heaps and queues.

Our bottleneck comes from reading in the input and evaluating each armor piece. Because of this, we can't do better than O(n) time, and I take advantage of this in `equipMisc()` by iteratively checking every armor piece again in O(n) time. O(n) plus O(n) is O(2n), which is still on the order of O(n)--linear time.

Although a greedy algorithm is theoretically a close approximation to the optimal solution, I decided to make that tradeoff for a better runtime complexity. For a combinatorial optimization problem like this, I wanted to strategize my solution around these tradeoffs and challenge myself to make it more efficient and elegant than exhaustive search.

My solution should be successful with other inventories--given the format of the input, and any edge cases to consider. For example, zero and negative values won't work in certain cases--we can't divide by zero. I also assume that all inventories will have valid values. Otherwise, my solution should give a close approximation to the most optimal value for other inventories.
