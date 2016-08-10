CREATE DATABASE bookmark;

-- create user
GRANT USAGE ON *.* TO yoda IDENTIFIED BY 'jedi123';
GRANT ALL PRIVILEGES ON bookmark.* TO yoda;

USE bookmark;

-- create table
CREATE TABLE bookmark (
	bookmark_id INT NOT NULL AUTO_INCREMENT,
	bookmark_title VARCHAR(25) NOT NULL,
	bookmark_url VARCHAR(200) NOT NULL,
	
	PRIMARY KEY (bookmark_id)
);
