-- MySQL dump 10.16  Distrib 10.1.40-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: digger_db
-- ------------------------------------------------------
-- Server version	10.1.40-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `report_histories`
--

DROP TABLE IF EXISTS `report_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_histories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zone_id` varchar(255) NOT NULL,
  `soa_email` varchar(72) NOT NULL,
  `soa_serialno` bigint(20) NOT NULL,
  `severity_level` tinyint(4) NOT NULL DEFAULT '0',
  `severity_urgent` int(11) NOT NULL DEFAULT '0',
  `severity_high` int(11) NOT NULL DEFAULT '0',
  `severity_medium` int(11) NOT NULL DEFAULT '0',
  `severity_low` int(11) NOT NULL DEFAULT '0',
  `severity_info` int(11) NOT NULL DEFAULT '0',
  `dnssec_enabled` tinyint(4) NOT NULL DEFAULT '0',
  `soa_inconsistency` tinyint(4) NOT NULL DEFAULT '0',
  `open_recursive` tinyint(4) NOT NULL DEFAULT '0',
  `open_axfr` tinyint(4) NOT NULL DEFAULT '0',
  `non_compliant_edns` tinyint(4) NOT NULL DEFAULT '0',
  `server_not_working` tinyint(4) NOT NULL DEFAULT '0',
  `rrset_inconsistency` tinyint(4) NOT NULL DEFAULT '0',
  `number_of_servers` int(11) NOT NULL DEFAULT '0',
  `number_of_problems` int(11) NOT NULL DEFAULT '0',
  `json_report` mediumtext,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `zone_id` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_histories`
--

LOCK TABLES `report_histories` WRITE;
/*!40000 ALTER TABLE `report_histories` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reports` (
  `zone_id` varchar(255) NOT NULL,
  `soa_email` varchar(72) NOT NULL,
  `soa_serialno` bigint(20) NOT NULL,
  `severity_level` tinyint(4) NOT NULL DEFAULT '0',
  `severity_urgent` int(11) NOT NULL DEFAULT '0',
  `severity_high` int(11) NOT NULL DEFAULT '0',
  `severity_medium` int(11) NOT NULL DEFAULT '0',
  `severity_low` int(11) NOT NULL DEFAULT '0',
  `severity_info` int(11) NOT NULL DEFAULT '0',
  `dnssec_enabled` tinyint(4) NOT NULL DEFAULT '0',
  `soa_inconsistency` tinyint(4) NOT NULL DEFAULT '0',
  `open_recursive` tinyint(4) NOT NULL DEFAULT '0',
  `open_axfr` tinyint(4) NOT NULL DEFAULT '0',
  `non_compliant_edns` tinyint(4) NOT NULL DEFAULT '0',
  `server_not_working` tinyint(4) NOT NULL DEFAULT '0',
  `rrset_inconsistency` tinyint(4) NOT NULL DEFAULT '0',
  `number_of_servers` int(11) NOT NULL DEFAULT '0',
  `number_of_problems` int(11) NOT NULL DEFAULT '0',
  `json_report` mediumtext,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scene_users`
--

DROP TABLE IF EXISTS `scene_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scene_users` (
  `scene_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role` enum('owner','operator','viewer') NOT NULL DEFAULT 'viewer',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`scene_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scene_users`
--

LOCK TABLES `scene_users` WRITE;
/*!40000 ALTER TABLE `scene_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `scene_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scene_zones`
--

DROP TABLE IF EXISTS `scene_zones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scene_zones` (
  `scene_id` int(11) NOT NULL,
  `zone_id` varchar(255) NOT NULL,
  `zone_name` varchar(24) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `contact_email` varchar(64) NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`scene_id`,`zone_id`),
  KEY `zone_id` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scene_zones`
--

LOCK TABLES `scene_zones` WRITE;
/*!40000 ALTER TABLE `scene_zones` DISABLE KEYS */;
/*!40000 ALTER TABLE `scene_zones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scenes`
--

DROP TABLE IF EXISTS `scenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `scene_name` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scenes`
--

LOCK TABLES `scenes` WRITE;
/*!40000 ALTER TABLE `scenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `scenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` bigint(20) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `role` int(11) NOT NULL DEFAULT '0',
  `nickname` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zone_users`
--

DROP TABLE IF EXISTS `zone_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zone_users` (
  `zone_id` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`zone_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zone_users`
--

LOCK TABLES `zone_users` WRITE;
/*!40000 ALTER TABLE `zone_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `zone_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zones`
--

DROP TABLE IF EXISTS `zones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zones` (
  `id` varchar(255) NOT NULL,
  `parent_zone` varchar(255) DEFAULT NULL,
  `master_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parent_idx` (`parent_zone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zones`
--

LOCK TABLES `zones` WRITE;
/*!40000 ALTER TABLE `zones` DISABLE KEYS */;
INSERT INTO `zones` VALUES ('adm.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('adsl.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('amazon.com','com',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('ancos.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('apple.com','com',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('asia.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('astro.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('atm.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('au.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('bdes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('berkeley.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('bges.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('bles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('blps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('bses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('caes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('cc.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('ccps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('cct.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('ccu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('ce.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('cges.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('cgps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('cgu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('chem.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('ches.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:46');
INSERT INTO `zones` VALUES ('chjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('chjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('chses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('chu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('chvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cies.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cisco.com','com',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cjcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ck.tp.edu.tw','tp.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ckjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ckps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cksh.tp.edu.tw','tp.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ckvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cl.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('clhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cljhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cloud.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cloudflare.com','com',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('clps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('clvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('clvsc.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cme.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cmgsh.tp.edu.tw','tp.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cmu.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cmu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cornell.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cpps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cpshs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cpu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('csghs.tp.edu.tw','tp.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('csie.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('csmu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('csps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('csrsr.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ctbc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ctes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cv.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cwjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cycu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('cyvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('daes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dayes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dces.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dches.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dcjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dd.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dd.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('ddyu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:47');
INSERT INTO `zones` VALUES ('dges.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dgjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dises.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('djes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dkes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dljh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dop.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dorm.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dpjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dpps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dsjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dsjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dsps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dssh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dxhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dyjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dyjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dyps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dysh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('dyu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('earth.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('edu.tw','tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('ee.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('emba.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('erdc.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('ev.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('exam.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('faes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fdes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fdhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('ffjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fg.tp.edu.tw','tp.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fges.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fgu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fju.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fkjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fsps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fsvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('ftes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fxsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('fyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('g.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('geo.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gep.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gies.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gijh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gish.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gljh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gmail.com','com',NULL,NULL,NULL,'2019-06-14 01:01:48');
INSERT INTO `zones` VALUES ('gmes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gmjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('google.com','com',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gsjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('gyps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('happy.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('harvard.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hcc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hcrc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hfjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hfps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hfu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hmjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hnjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hs.ntnu.edu.tw','ntnu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hshs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('htes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hwes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('hyps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('ihs.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('iiietc.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('in.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('ios.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('ipv6.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('is.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('isu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jbes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jdes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jdps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jfes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jgjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jhjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jkes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jsjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jtes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jtps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('jwes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('kces.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('khes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('khps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('kjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:49');
INSERT INTO `zones` VALUES ('kjjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kmu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('knu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kpps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kuhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('kves.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lab.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lab2.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('laps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lc.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lces.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('leses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lfes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lges.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lhvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lib.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lkjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lnses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('loses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lsjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lsps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lst.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('ltes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('ltjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('ltsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lulin.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('lyjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:50');
INSERT INTO `zones` VALUES ('math.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mdu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('me.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mgt.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mit.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mm.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('mmc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nashs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nccu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nchu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ncku.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ncnu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nctu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ncu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ncue.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ncyu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ndes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ndhu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nhu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('niu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('njes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nkes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nkjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nknu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nksh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nlhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nljh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nlps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nmes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nmps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nptu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nqu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nsps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nsysu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nthu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntnu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntou.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntpc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntpu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntsu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nttu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntua.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntue.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntupes.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('ntut.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nuk.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nutn.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nuu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('nyc.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('office365.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('pccu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('pces.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('pcue.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('phps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('phsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:51');
INSERT INTO `zones` VALUES ('phy.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pine.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pjjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pmes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pnjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('psees.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('psjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('ptes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('ptjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('puses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pymhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('pzps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('qpjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:52');
INSERT INTO `zones` VALUES ('recast.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rfes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rhps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rmes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rpes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rpjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('rses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('ryes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('ryjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('saes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sajes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sales.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sames.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('scc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('scu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sdes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sdps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sfes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sgps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('shes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('shlps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('shps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('shu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('simes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('simps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sjjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sjps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('skes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('skps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('slies.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('slps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('smes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('smjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('smps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('snwes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('spes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('spps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sres.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('ss.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sshes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('ssps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sssh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('stanford.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('stat.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('stes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('stps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('swes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('swjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('swps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('sybbi.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('szes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('szps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('taes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('tajh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('taps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('tates.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:53');
INSERT INTO `zones` VALUES ('tcjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tcjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tcu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tdes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tdjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('thes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('thps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('thsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('thu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tku.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tmes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tmps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tmu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tnnua.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tnua.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('toko.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tp.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tsad.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tsu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ttes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ttjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ttps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ttu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('twaren.net','net',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('twes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('twjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyai.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyas.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('typs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tyrc.ncu.edu.tw','ncu.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tysh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('tzsavs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ucla.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('uiuc.edu','edu',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('ukn.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('uniforcetest.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('usc.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('utaipei.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wcjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wcjps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('weses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('whes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('whjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('whps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wlsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wqes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('wsps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('xcms.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:54');
INSERT INTO `zones` VALUES ('xwsh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yaes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yajh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yfes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yfms.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ygjps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yhes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ym.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ymes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ymhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ymjh.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ymjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ymps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ypvs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yres.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yses.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ysles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('ysps.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yuda.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yushes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('yzu.edu.tw','edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('zjes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:55');
INSERT INTO `zones` VALUES ('zles.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:56');
INSERT INTO `zones` VALUES ('zmjhs.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:56');
INSERT INTO `zones` VALUES ('zqes.tyc.edu.tw','tyc.edu.tw',NULL,NULL,NULL,'2019-06-14 01:01:56');
/*!40000 ALTER TABLE `zones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-21 10:19:06
