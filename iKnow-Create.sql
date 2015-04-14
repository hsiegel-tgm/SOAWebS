-- iKnow Create-Script
-- Martin Haidn
-- 11-04-2015

DROP DATABASE IF EXISTS iknow;
CREATE DATABASE iknow;

DROP TABLE IF EXISTS knowledgebase;
CREATE DATABASE knowledgebase(
	id INT NOT NULL auto_increment,
	topic VARCHAR(10),
	text VARCHAR(100),
	PRIMARY KEY(id)
)ENGINE=INNODB;

GRANT ALL ON iknow.* TO 'vsdb'@'localhost' IDENTIFIED BY 'letmein';

INSERT INTO knowledgebase VALUES('Test1', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut.');
INSERT INTO knowledgebase VALUES('Test2', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut.');