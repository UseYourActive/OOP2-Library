-- Insert authors
INSERT INTO authors (name) VALUES
  ('Author1'),
  ('Author2'),
  ('Author3'),
  ('Author4'),
  ('Author5'),
  ('Author6'),
  ('Author7'),
  ('Author8'),
  ('Author9'),
  ('Author10');

-- Insert book inventories
INSERT INTO book_inventories (inventory_id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

-- Insert books
INSERT INTO books (book_id, number_of_times_used, publish_date, title, resume, author_id, genres, book_status, inventory_id)
VALUES
  (1, 3, '2000', 'Book1', 'Summary of Book1', 1, 'FICTION', 'AVAILABLE', 1),
  (2, 2, '2010', 'Book2', 'Summary of Book2', 2, 'NON_FICTION', 'LENT', 2),
  (3, 5, '1995', 'Book3', 'Summary of Book3', 3, 'MYSTERY', 'ARCHIVED', 3),
  (4, 4, '2015', 'Book4', 'Summary of Book4', 4, 'SCIENCE_FICTION', 'AVAILABLE', 4),
  (5, 1, '2008', 'Book5', 'Summary of Book5', 5, 'ROMANCE', 'LENT', 5),
  (6, 6, '1990', 'Book6', 'Summary of Book6', 6, 'FANTASY', 'ARCHIVED', 6),
  (7 ,8, '2005', 'Book7', 'Summary of Book7', 7, 'HORROR', 'AVAILABLE', 7),
  (8, 7, '2012', 'Book8', 'Summary of Book8', 8, 'THRILLER', 'LENT', 8),
  (9, 9, '1985', 'Book9', 'Summary of Book9', 9, 'HISTORY', 'ARCHIVED', 9),
  (10, 10, '2002', 'Book10', 'Summary of Book10', 10, 'BIOGRAPHY', 'AVAILABLE', 10);

UPDATE book_inventories SET book_id = inventory_id;
