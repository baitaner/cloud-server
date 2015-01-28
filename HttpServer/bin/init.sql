/*
Navicat MySQL Data Transfer

Source Server         : 10.180.45.197
Source Server Version : 50534
Source Host           : 10.180.45.197:3306
Source Database       : shasta_db

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2014-03-05 22:09:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_device`
-- ----------------------------
DROP TABLE IF EXISTS `s_device`;
CREATE TABLE `s_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(100) NOT NULL,
  `house_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `firmware_version` varchar(50) DEFAULT NULL,
  `protocol_version` int(11) DEFAULT NULL,
  `mac` varchar(50) DEFAULT NULL,
  `is_register` tinyint(2) NOT NULL DEFAULT '0',
  `is_bind` tinyint(2) NOT NULL,
  `register_time` bigint(20) DEFAULT NULL,
  `bind_time` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `device_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_device
-- ----------------------------
INSERT INTO `s_device` VALUES ('1', '123456789', '0', '8', '1.0', '1', '0000.0000.0000', '1', '1', '1394027262313', '1394027297971', '1394027262313', '7cd2ac6cc5a5d1b3478aaaef38eb0efd');

-- ----------------------------
-- Table structure for `s_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('8', 'jkl', '{\"cellphone\":\"aaa\",\"email\":\"c\",\"address\":\"d\"}', '7815696ecbf1c96e6894b779456d330e', '1394027262313');
