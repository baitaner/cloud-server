/*
Navicat MySQL Data Transfer

Source Server         : 123.56.95.160
Source Server Version : 50535
Source Host           : 123.56.95.160:3306
Source Database       : baitaner_db

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2014-05-25 16:17:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bt_user`
-- ----------------------------
DROP TABLE IF EXISTS `bt_user`;
CREATE TABLE `bt_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `group_email` varchar(100) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `is_auth` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1： 邮箱未激活（注册后激活） 2：邮箱激活',
  `password` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未认证，或未加入任何公司  2：加入某个公司',
  `role` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：普通用户 2：公司管理员 3：后台管理员',
  `group_id` bigint(20) DEFAULT NULL,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `login_time` TIMESTAMP ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_user
-- ----------------------------

-- ----------------------------
-- Table structure for `bt_group`
-- ----------------------------
DROP TABLE IF EXISTS `bt_group`;
CREATE TABLE `bt_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `email_postfix` varchar(100) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_group
-- ----------------------------

-- ----------------------------
-- Table structure for `bt_goods`
-- ----------------------------
DROP TABLE IF EXISTS `bt_goods`;
CREATE TABLE `bt_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` TEXT DEFAULT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `group_id` BIGINT(20) NOT NULL COMMENT '考虑到更换公司的信息',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未发布（新建）2：发布 3：取消 4：完成 5：结束',
  `previous_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未发布（新建）2：发布 3：取消 4：完成 5：结束',
  `is_lock` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: 未锁定  2：锁定',
  `price` float(11,2) NOT NULL DEFAULT '0',
  `total` int(11) NOT NULL DEFAULT '0',
  `sell_count` int(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  `expire_time` timestamp NOT NULL,
  `update_time` timestamp,
  `publish_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `bt_goods_photo`
-- ----------------------------
DROP TABLE IF EXISTS `bt_goods_photo`;
CREATE TABLE `bt_goods_photo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `photo_url` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_goods_photo
-- ----------------------------

-- ----------------------------
-- Table structure for `bt_indent`
-- ----------------------------
DROP TABLE IF EXISTS `bt_indent`;
CREATE TABLE `bt_indent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：新增订单  正在进行交易 2：取消订单 3：订单结束 交易完成',
  `buy_count` int(11) NOT NULL DEFAULT '0',
  `description` TEXT DEFAULT NULL,
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  `update_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bt_indent
-- ----------------------------
