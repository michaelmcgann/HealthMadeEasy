
1. How food item details are stored
Context: User provides a food item with the given macros for the weight that is most convenient to then, ie what is on 
the label. A decision should be made on how these values are stored.
Decision: Values will be converted to be stored per gram, meaning each macro will be divided by the amount in grams given.
Consequences: User can simply input what is on the label and the app will do the rest, what's more, when a food item is
reused, the user will only need to state the grams used and the app will calculate the macros.

2. How log entries are stored in the database
Context: When a meal has been created and the user wants to log it, there needs to be a consistent way in which entries
are stored; as meals or separated into individual food items. 
Decision: As an MVP, food items will be separated from meals and stored as logs individually.