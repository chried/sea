/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : sea

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 23/10/2019 16:56:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `parent_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1184268940045864961', NULL, '系统管理', 'system', 'url-1', '_qq');
INSERT INTO `menu` VALUES ('1184268940154916865', NULL, 'name-2', 'code-2', 'url-2', '_qq');
INSERT INTO `menu` VALUES ('1185009311382134786', '1184268940045864961', '用户管理', 'userManage', 'userManage', '_qq');
INSERT INTO `menu` VALUES ('1185009311453437954', '1184268940045864961', '角色管理', 'roleManage', 'roleManage', '_qq');
INSERT INTO `menu` VALUES ('1185009311512158209', '1184268940045864961', '菜单管理', 'menuManage', 'menuManage', '_qq');
INSERT INTO `menu` VALUES ('1186562633700462593', '1184268940045864961', '权限管理', 'permissionManage', 'permissionManage', '_qq');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` bit(1) NULL DEFAULT NULL COMMENT '消息分类',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息标题',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息内容',
  `read` bit(1) NULL DEFAULT NULL COMMENT '是否已读',
  `role_id` bigint(19) NULL DEFAULT NULL COMMENT '接收权限',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `read_date` datetime(0) NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `sign` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1178504627830034434', 'permission-1', 'permission-1', '/order/get');
INSERT INTO `permission` VALUES ('1178504628027166721', 'permission-2', 'permission-2', '/auth/index');
INSERT INTO `permission` VALUES ('1178504628064915458', 'permission-3', 'ROLE_ANONYMOUS', '/login');
INSERT INTO `permission` VALUES ('1178504628127830018', 'permission-4', 'permission-4', '/order/get/4');
INSERT INTO `permission` VALUES ('1178504628173967361', 'permission-5', 'permission-5', '/order/get/5');
INSERT INTO `permission` VALUES ('1178504628228493314', 'permission-6', 'permission-6', '/order/get/6');
INSERT INTO `permission` VALUES ('1178504628287213570', 'permission-7', 'permission-7', '/order/get/7');
INSERT INTO `permission` VALUES ('1178504628329156609', 'permission-8', 'permission-8', '/order/get/8');
INSERT INTO `permission` VALUES ('1178504628396265473', 'permission-9', 'permission-9', '/order/get/9');
INSERT INTO `permission` VALUES ('1178504628450791425', 'permission-10', 'permission-10', '/order/get/10');
INSERT INTO `permission` VALUES ('1178504628492734466', 'permission-11', 'permission-11', '/order/get/11');
INSERT INTO `permission` VALUES ('1178504628547260418', 'permission-12', 'permission-12', '/order/get/12');
INSERT INTO `permission` VALUES ('1178504628585009153', 'permission-13', 'permission-13', '/order/get/13');
INSERT INTO `permission` VALUES ('1178504628635340801', 'permission-14', 'permission-14', '/order/get/14');
INSERT INTO `permission` VALUES ('1178504628685672449', 'permission-15', 'permission-15', '/order/get/15');
INSERT INTO `permission` VALUES ('1178504628769558529', 'permission-16', 'permission-16', '/order/get/16');
INSERT INTO `permission` VALUES ('1178504628828278785', 'permission-17', 'permission-17', '/order/get/17');
INSERT INTO `permission` VALUES ('1178504628861833218', 'permission-18', 'permission-18', '/order/get/18');
INSERT INTO `permission` VALUES ('1178504628903776257', 'permission-19', 'permission-19', '/order/get/19');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sign` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1178505622140203009', 'role-1', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('1178505622387666945', 'role-2', 'role-2');
INSERT INTO `role` VALUES ('1178505622467358722', 'role-3', 'role-3');
INSERT INTO `role` VALUES ('1178505622509301761', 'role-4', 'role-4');
INSERT INTO `role` VALUES ('1178505622551244801', 'role-5', 'role-5');
INSERT INTO `role` VALUES ('1178505622626742273', 'role-6', 'role-6');
INSERT INTO `role` VALUES ('1178505622630936578', 'role-7', 'role-7');
INSERT INTO `role` VALUES ('1178505622744182786', 'role-8', 'role-8');
INSERT INTO `role` VALUES ('1178505622786125826', 'role-9', 'role-9');
INSERT INTO `role` VALUES ('1178505622823874562', 'role-10', 'role-10');
INSERT INTO `role` VALUES ('1186107351820021762', '12', '12');
INSERT INTO `role` VALUES ('1186107907020042241', '33', '33');
INSERT INTO `role` VALUES ('1186184216194764802', '44551', '44551');
INSERT INTO `role` VALUES ('1186459594771537921', 'rrra', 'rrra');
INSERT INTO `role` VALUES ('1186459836690604034', '管理员', 'manage');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `menu_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1186924788912189441', '1178505622140203009', '1184268940154916865');
INSERT INTO `role_menu` VALUES ('1186924788920578050', '1178505622140203009', '1184268940045864961');
INSERT INTO `role_menu` VALUES ('1186924788928966657', '1178505622140203009', '1185009311453437954');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1178508944213422081', '1178504628492734466', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944276336642', '1178504628547260418', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944318279682', '1178504628585009153', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944372805633', '1178504628635340801', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944498634753', '1178504628685672449', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944540577793', '1178504628769558529', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944586715137', '1178504628828278785', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944641241089', '1178504628861833218', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1178508944771264513', '1178504628903776257', '1178505622387666945');
INSERT INTO `role_permission` VALUES ('1186928525865050113', '1178504627830034434', '1178505622140203009');
INSERT INTO `role_permission` VALUES ('1186928525886021634', '1178504628027166721', '1178505622140203009');
INSERT INTO `role_permission` VALUES ('1186928525886021635', '1178504628064915458', '1178505622140203009');

-- ----------------------------
-- Table structure for url_error
-- ----------------------------
DROP TABLE IF EXISTS `url_error`;
CREATE TABLE `url_error`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `error_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求类型',
  `msg` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `error_code` smallint(5) NULL DEFAULT NULL COMMENT '错误码',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_error
-- ----------------------------
INSERT INTO `url_error` VALUES ('1184299469818556418', 'ajax', '', 404, 'http://localhost:60456/message/count', '2019-10-16 10:46:11', '2019-10-17 08:44:56');
INSERT INTO `url_error` VALUES ('1184388517740294146', 'ajax', '', 500, 'http://localhost:60456/admin/user/getUserInfo', '2019-10-16 16:40:02', '2019-10-21 14:21:25');
INSERT INTO `url_error` VALUES ('1184631525081608194', 'ajax', '', 404, 'http://localhost:60456/adminmessage/count', '2019-10-17 08:45:40', '2019-10-17 08:45:40');
INSERT INTO `url_error` VALUES ('1184632063055622146', 'ajax', '', 500, 'http://localhost:60456/admin/message/count', '2019-10-17 08:47:48', '2019-10-17 11:04:12');
INSERT INTO `url_error` VALUES ('1186099708468580354', 'ajax', '', 500, 'http://localhost:60456/login', '2019-10-21 09:59:42', '2019-10-21 09:59:42');
INSERT INTO `url_error` VALUES ('1186103654083907586', 'ajax', '', 404, 'http://localhost:60456/admin/role/addOrEdit?id=&name=admin&sign=123456', '2019-10-21 10:15:23', '2019-10-21 10:15:23');
INSERT INTO `url_error` VALUES ('1186174794416418817', 'ajax', '', 500, 'http://localhost:60456/admin/role/addOrEdit', '2019-10-21 14:58:04', '2019-10-21 15:11:14');
INSERT INTO `url_error` VALUES ('1186207421701726210', 'ajax', '', 404, 'http://localhost:60456/', '2019-10-21 17:07:43', '2019-10-21 17:07:43');
INSERT INTO `url_error` VALUES ('1186484549446004738', 'ajax', '', 404, 'http://localhost:60456/admin/menu/addOrUpdate1', '2019-10-22 11:28:55', '2019-10-22 11:28:55');
INSERT INTO `url_error` VALUES ('1186518619827671041', 'ajax', '', 405, 'http://localhost:60456/admin/menu/queryChild?parentId=', '2019-10-22 13:44:18', '2019-10-22 13:44:43');
INSERT INTO `url_error` VALUES ('1186549511186972674', 'ajax', '', 404, 'http://localhost:60456/admin/user/query', '2019-10-22 15:47:03', '2019-10-22 15:47:23');
INSERT INTO `url_error` VALUES ('1186565531985498113', 'ajax', '', 404, 'http://localhost:60456/admin/permission/addOrEdit', '2019-10-22 16:50:43', '2019-10-22 16:50:49');
INSERT INTO `url_error` VALUES ('1186844075479158785', 'ajax', NULL, NULL, 'http://localhost:60456/admin/user/handleUserRole', '2019-10-23 11:17:33', '2019-10-23 11:30:49');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `x_status` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `x_edition` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `portrait` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `lock_date` datetime(0) NULL DEFAULT NULL COMMENT '锁定时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1177027183994355714', 'chried', '15884499510', '2019-09-26 09:08:43', '2019-09-26 09:08:43', 'ACTIVE', 'abb277ca-5841-40a8-8a49-582ddbf0e65d', '$2a$10$lcXGxwr5aE3Y6DQLTmOSSeSsmCTXTowQRwLg3RccBaLMjrE27aWXq', 'gwbbest@vip.qq.com', 'https://avatar.csdn.net/B/E/F/3_spring21st.jpg', '高小帅', '2019-09-28 17:17:53');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `expire_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1186871587009613826', '1177027183994355714', '1178505622140203009', '2020-10-23 13:06:52');
INSERT INTO `user_role` VALUES ('1186871587018002433', '1177027183994355714', '1178505622387666945', '2020-10-23 13:06:52');
INSERT INTO `user_role` VALUES ('1186871587018002434', '1177027183994355714', '1186459836690604034', '2020-10-23 13:06:52');

SET FOREIGN_KEY_CHECKS = 1;
