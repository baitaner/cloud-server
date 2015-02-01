/*
Navicat MySQL Data Transfer

Source Server         : 123.56.95.160
Source Server Version : 50535
Source Host           : 123.56.95.160:3306
Source Database       : baitaner_log_db

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2014-05-25 16:17:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bt_user_message`
-- ----------------------------
DROP TABLE IF EXISTS `bt_user_message`;
CREATE TABLE `bt_user_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未读  2：已读 ',
  `content` text DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_user_message
-- ----------------------------