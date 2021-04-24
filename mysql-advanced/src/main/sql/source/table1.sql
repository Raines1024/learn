/*
 Navicat Premium Data Transfer

 Source Server         : 172.29.32.20
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 172.29.32.20:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 22/04/2021 10:10:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for table1
-- ----------------------------
DROP TABLE IF EXISTS `table1`;
CREATE TABLE `table1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parent_id` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of table1
-- ----------------------------
BEGIN;
INSERT INTO `table1` VALUES (1, 'Home', 0);
INSERT INTO `table1` VALUES (2, 'About', 1);
INSERT INTO `table1` VALUES (3, 'Contact', 1);
INSERT INTO `table1` VALUES (4, 'Legal', 2);
INSERT INTO `table1` VALUES (5, 'Privacy', 4);
INSERT INTO `table1` VALUES (6, 'Products', 1);
INSERT INTO `table1` VALUES (7, 'Products', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
