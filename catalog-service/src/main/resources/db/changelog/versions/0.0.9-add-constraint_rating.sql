ALTER TABLE products
    ADD CONSTRAINT check_rating CHECK (rating IS NULL OR rating >= 1);