-- iKnow Create-Script
-- Martin Haidn
-- 11-04-2015

DROP DATABASE IF EXISTS iknow;
CREATE DATABASE iknow;
USE iknow;

DROP TABLE IF EXISTS knowledgebase;


CREATE TABLE knowledgebase(
	id INT NOT NULL auto_increment,
	topic VARCHAR(10),
	text VARCHAR(100),
	PRIMARY KEY(id)
)ENGINE=INNODB;

GRANT ALL ON iknow.* TO 'vsdb'@'localhost' IDENTIFIED BY 'letmein';

INSERT INTO knowledgebase VALUES(1,'Test1', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut.');
INSERT INTO knowledgebase VALUES(2,'Test2', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut.');

INSERT INTO knowledgebase VALUES(3,'Test3', 'Hannah hat einen Hut.');
INSERT INTO knowledgebase VALUES(4,'Test4', 'Wie geht es dir?');
INSERT INTO knowledgebase VALUES(5,'Test5', 'Yuhu du bist ein Hut');
