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

I'm a greedy person, and I want as much armor as I can get for as cheap as I can get it and do it fast. This problem reminds me of the knapsack problem, and no one wants to sit there and meticulously search through every possible combination. That could take a long time! How boring! The Witcher Geralt has wyverns to fight. The real kicker is that in order to have a full set, I need an extra piece of any armor type. So the amount of total combinations would take far too long to look through if I try to find every single one!

Instead, first what I'll do is prioritize each armor based on its armor value to cost ratio. I do this by dividing the armor value by its cost, and then picking the highest of that ratio for each type. That way I can try to buy the most amount of armor while spending the least amount of Crowns.

And here's where the details get juicy. I've already selected my four main armor types. All I have to do now is see how many Crowns I have left, and buy the armor with the highest armor value! I can be as greedy as I can get here, because I've reduced the problem to a simpler version!

## Implementation details

First, we read in the Armorer's inventory, which is currently stored in `inventory.txt`. We take each armor piece and store it in a max heap, or a Java `PriorityQueue`, and separate the pieces by armor type. We make our priority based on the ratio of armor value to cost, so that the highest ratio is at the head of the queue. I use a heap so that insertion is O(logn), and taking the max armor is constant.

We then take the head of each `PriorityQueue`, equip it to Geralt, and store a copy in an auxiliary data structure in case we need it again. Geralt now has a chest, legging, helmet, and boots with the highest armor value to cost ratio equipped.

Then we calculate how much we have left to spend, and greedily select the highest armor value we can afford out of all possible armor left over. We select that and equip it as our extra piece to complete the set.

However, we need to be careful that we aren't over-spending. If we did, we look at the remaining inventory and select the piece with the highest ratio again. It can be of any type. We swap our current armor for that new piece, and equip it in the appropriate slot.

We then grab the highest armor value we can afford again, and use that as our extra piece.

Once we've selected an armor set that satisfies the constraints, we print out the details.

I broke up code and made helper functions to try and repeat myself as little as possible. I also implemented each function with the goal of getting it as close to linear time as possible with as close to O(n) space as I could--although I am using auxiliary and temporary data structures to store information. However, my `quePointer` is only of size 4, and `aux` is bound to the size of the input but is proportional to my `PriorityQueue` sizes. I say as close to linear as possible, because I tried to avoid any O(nlogn) operations with heaps.

My solution should be successful with other inventories. Given the format of the input, and any edge cases to consider. For example, zero and negative values won't work in certain cases. I also assume that all inventories will have valid values. Otherwise, my solution should give a close approximation to the most optimal value for other inventories.
