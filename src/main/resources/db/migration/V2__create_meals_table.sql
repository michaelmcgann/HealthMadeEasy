CREATE TABLE meals (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

CREATE TABLE meal_food (
    meal_id UUID NOT NULL,
    food_id UUID NOT NULL,

    position INT NOT NULL DEFAULT 0,

    PRIMARY KEY (meal_id, food_id),

    CONSTRAINT fk_meal_food_meal
                       FOREIGN KEY (meal_id)
                       REFERENCES meals (id)
                       ON DELETE CASCADE,

    CONSTRAINT fk_meal_food_food
                       FOREIGN KEY (food_id)
                       REFERENCES foods (id)
                       ON DELETE NO ACTION

);

CREATE INDEX idx_meal_food_food_id on meal_food(food_id);