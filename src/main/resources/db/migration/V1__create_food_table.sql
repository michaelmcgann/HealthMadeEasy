CREATE TABLE IF NOT EXISTS foods (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,

    calories_per_gram NUMERIC(10,7) NOT NULL,
    protein_per_gram NUMERIC(10,7) NOT NULL,
    carbs_per_gram NUMERIC(10,7) NOT NULL,
    fat_per_gram NUMERIC(10,7) NOT NULL,

    CONSTRAINT foods_calories_nonneg CHECK (calories_per_gram >= 0),
    CONSTRAINT foods_protein_nonneg  CHECK (protein_per_gram  >= 0),
    CONSTRAINT foods_carbs_nonneg    CHECK (carbs_per_gram    >= 0),
    CONSTRAINT foods_fat_nonneg      CHECK (fat_per_gram      >= 0)
);
