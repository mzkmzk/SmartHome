/*
MySQL Data Transfer
Source Host: localhost
Source Database: pac_jiaju
Target Host: localhost
Target Database: pac_jiaju
Date: 2014/11/27 10:39:22
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `deviceid` int(20) NOT NULL AUTO_INCREMENT,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`deviceid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userphone` varchar(20) NOT NULL,
  `userpassword` varchar(20) NOT NULL,
  PRIMARY KEY (`userphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `device` VALUES ('1', '0');
INSERT INTO `device` VALUES ('2', '0');
INSERT INTO `device` VALUES ('3', '0');
INSERT INTO `device` VALUES ('4', '0');
INSERT INTO `device` VALUES ('5', '0');
INSERT INTO `device` VALUES ('6', '0');
INSERT INTO `device` VALUES ('7', '0');
INSERT INTO `device` VALUES ('8', '0');
INSERT INTO `device` VALUES ('9', '0');
INSERT INTO `device` VALUES ('10', '0');
INSERT INTO `device` VALUES ('11', '0');
