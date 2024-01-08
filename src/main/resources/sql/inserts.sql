--Insert Users
INSERT INTO users (password,role,username) VALUES
('admin','ADMIN','admin'),
('operator','OPERATOR','operator');


<!--INSERT INTO readers (email,first_name,last_name,middle_name,phone_number,reader_rating) VALUES
<!--('alexorozov@gmail.com','Aleks','Mihailov','Orozov','0878629416','TWO_STAR'),
<!--('reader1@email.com','Reader1','LastName','MiddleName','12345678910','NONE'),
<!--('reader2@email.com','Reader2','LastName','MiddleName','12345678910','NONE'),
<!--('bogson@abv.bg','Bogomil','Georgiev','Donkov','0876426263','THREE_STAR');

-- Insert authors
INSERT INTO authors (name) VALUES
  ('Jane Smith'),
  ('Michael Johnson'),
  ('Emily Brown'),
  ('David Lee'),
  ('Sarah Garcia'),
  ('Daniel Wilson'),
  ('Olivia Martinez'),
  ('William Thompson'),
  ('Sophia Davis'),
  ('Ethan Rodriguez');

-- Insert book inventories
INSERT INTO book_inventories (inventory_id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10),(11), (12), (13), (14), (15), (16), (17), (18), (19), (20);

-- Insert books
INSERT INTO books (book_id,number_of_times_used, publish_year, title, resume, author_id, genres, book_status, inventory_id)
VALUES
  (1,0, '2000', 'Book1', 'Summary of Book1', 1, 'FICTION', 'AVAILABLE', 1),
  (2,0, '2010', 'Book2', 'Summary of Book2', 2, 'NON_FICTION', 'AVAILABLE', 2),
  (3,0, '1995', 'Book3', 'Summary of Book3', 3, 'MYSTERY', 'AVAILABLE', 3),
  (4,0, '2015', 'Book4', 'Summary of Book4', 4, 'SCIENCE_FICTION', 'AVAILABLE', 4),
  (5,0, '2008', 'Book5', 'Summary of Book5', 5, 'ROMANCE', 'AVAILABLE', 5),
  (6,0, '1990', 'Book6', 'Summary of Book6', 6, 'FANTASY', 'AVAILABLE', 6),
  (7,0, '2005', 'Book7', 'Summary of Book7', 7, 'HORROR', 'AVAILABLE', 7),
  (8,0, '2012', 'Book8', 'Summary of Book8', 8, 'THRILLER', 'AVAILABLE', 8),
  (9,0, '1985', 'Book9', 'Summary of Book9', 9, 'HISTORY', 'AVAILABLE', 9),
  (10,0, '2002', 'Book10', 'Summary of Book10', 10, 'BIOGRAPHY', 'AVAILABLE', 10),
  (11,0, '2000', 'Book11', 'Summary of Book11', 1, 'FICTION', 'AVAILABLE', 11),
  (12,0, '2010', 'Book12', 'Summary of Book12', 2, 'NON_FICTION', 'AVAILABLE', 12),
  (13,0, '1995', 'Book13', 'Summary of Book13', 3, 'MYSTERY', 'AVAILABLE', 13),
  (14,0, '2015', 'Book14', 'Summary of Book14', 4, 'SCIENCE_FICTION', 'AVAILABLE', 14),
  (15,0, '2008', 'Book15', 'Summary of Book15', 5, 'ROMANCE', 'AVAILABLE', 15),
  (16,0, '1990', 'Book16', 'Summary of Book16', 6, 'FANTASY', 'AVAILABLE', 16),
  (17,0, '2005', 'Book17', 'Summary of Book17', 7, 'HORROR', 'AVAILABLE', 17),
  (18,0, '2012', 'Book18', 'Summary of Book18', 8, 'THRILLER', 'AVAILABLE', 18),
  (19,0, '1985', 'Book19', 'Summary of Book19', 9, 'HISTORY', 'AVAILABLE', 19),
  (20,0, '2002', 'Book20', 'Summary of Book20', 10, 'BIOGRAPHY', 'AVAILABLE', 20);

UPDATE book_inventories SET book_id = inventory_id;
