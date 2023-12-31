-- Create a trigger function
CREATE OR REPLACE FUNCTION after_book_insert()
RETURNS TRIGGER AS $$
BEGIN
    -- Check if the book inventory entry exists
    IF EXISTS (SELECT 1 FROM book_inventories WHERE inventory_id = NEW.inventory_id) THEN
        -- Update the book_id in book_inventories
        UPDATE book_inventories SET book_id = NEW.id WHERE inventory_id = NEW.inventory_id;
    ELSE
        -- Insert a new entry in book_inventories
        INSERT INTO book_inventories (inventory_id, book_id) VALUES (NEW.inventory_id, NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach the trigger to the books table
CREATE TRIGGER after_book_insert
AFTER INSERT
ON books
FOR EACH ROW
EXECUTE FUNCTION after_book_insert();