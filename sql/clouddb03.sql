/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.28 : Database - clouddb03
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`clouddb03` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `clouddb03`;

/*Table structure for table `dept` */

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `deptno` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(50) NOT NULL COMMENT '部门名称',
  `db_source` varchar(50) NOT NULL COMMENT '数据库地址',
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Data for the table `dept` */

insert  into `dept`(`deptno`,`dname`,`db_source`) values 
(1,'华南部门','DBSOURCE()'),
(2,'华北部门','DBSOURCE()'),
(3,'人事部门','DBSOURCE()'),
(4,'财务部门','DBSOURCE()');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
