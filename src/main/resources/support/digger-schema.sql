-- MariaDB dump 10.19  Distrib 10.6.5-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: digger_db
-- ------------------------------------------------------
-- Server version	10.6.5-MariaDB

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
-- Table structure for table `country_ranking`
--

DROP TABLE IF EXISTS `country_ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country_ranking` (
  `event_id` int(11) NOT NULL,
  `country_code` char(6) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `zones` int(11) DEFAULT NULL,
  `dnssec` int(11) NOT NULL,
  `ipv6` int(11) NOT NULL,
  `edns` int(11) NOT NULL,
  `rrset` int(11) NOT NULL,
  `axfr` int(11) NOT NULL,
  `recursion` int(11) NOT NULL,
  `normal` int(11) NOT NULL,
  `info` int(11) NOT NULL,
  `low` int(11) NOT NULL,
  `medium` int(11) NOT NULL,
  `high` int(11) NOT NULL,
  `urgent` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`event_id`,`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `query_queue`
--

DROP TABLE IF EXISTS `query_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `query_queue` (
  `zone_id` varchar(255) NOT NULL,
  `service_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`zone_id`),
  KEY `created_at_idx` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ranking_events`
--

DROP TABLE IF EXISTS `ranking_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranking_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` char(12) NOT NULL,
  `name` varchar(128) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag` (`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_histories`
--

DROP TABLE IF EXISTS `report_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_histories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zone_id` varchar(255) NOT NULL,
  `parent_zone` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `soa_email` varchar(72) NOT NULL,
  `soa_serialno` bigint(20) NOT NULL,
  `severity_level` tinyint(4) NOT NULL DEFAULT 0,
  `severity_urgent` int(11) NOT NULL DEFAULT 0,
  `severity_high` int(11) NOT NULL DEFAULT 0,
  `severity_medium` int(11) NOT NULL DEFAULT 0,
  `severity_low` int(11) NOT NULL DEFAULT 0,
  `severity_info` int(11) NOT NULL DEFAULT 0,
  `dnssec_enabled` tinyint(4) NOT NULL DEFAULT 0,
  `ipv6_available` tinyint(4) DEFAULT NULL,
  `soa_inconsistency` tinyint(4) NOT NULL DEFAULT 0,
  `open_recursive` tinyint(4) NOT NULL DEFAULT 0,
  `open_axfr` tinyint(4) NOT NULL DEFAULT 0,
  `non_compliant_edns` tinyint(4) NOT NULL DEFAULT 0,
  `server_not_working` tinyint(4) NOT NULL DEFAULT 0,
  `rrset_inconsistency` tinyint(4) NOT NULL DEFAULT 0,
  `number_of_servers` int(11) NOT NULL DEFAULT 0,
  `number_of_problems` int(11) NOT NULL DEFAULT 0,
  `json_report` mediumtext DEFAULT NULL,
  `remote_address` varchar(128) NOT NULL DEFAULT '',
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `zone_id` (`zone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34660 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reports` (
  `zone_id` varchar(255) NOT NULL,
  `parent_zone` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `soa_email` varchar(72) NOT NULL,
  `soa_serialno` bigint(20) NOT NULL,
  `severity_level` tinyint(4) NOT NULL DEFAULT 0,
  `severity_urgent` int(11) NOT NULL DEFAULT 0,
  `severity_high` int(11) NOT NULL DEFAULT 0,
  `severity_medium` int(11) NOT NULL DEFAULT 0,
  `severity_low` int(11) NOT NULL DEFAULT 0,
  `severity_info` int(11) NOT NULL DEFAULT 0,
  `dnssec_enabled` tinyint(4) NOT NULL DEFAULT 0,
  `ipv6_available` tinyint(4) NOT NULL DEFAULT 0,
  `soa_inconsistency` tinyint(4) NOT NULL DEFAULT 0,
  `open_recursive` tinyint(4) NOT NULL DEFAULT 0,
  `open_axfr` tinyint(4) NOT NULL DEFAULT 0,
  `non_compliant_edns` tinyint(4) NOT NULL DEFAULT 0,
  `server_not_working` tinyint(4) NOT NULL DEFAULT 0,
  `rrset_inconsistency` tinyint(4) NOT NULL DEFAULT 0,
  `number_of_servers` int(11) NOT NULL DEFAULT 0,
  `number_of_problems` int(11) NOT NULL DEFAULT 0,
  `json_report` mediumtext DEFAULT NULL,
  `remote_address` varchar(128) NOT NULL DEFAULT '',
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`zone_id`),
  KEY `updated_at_index` (`updated_at`),
  KEY `parent_zone_index` (`parent_zone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `scene_report_view`
--

DROP TABLE IF EXISTS `scene_report_view`;
/*!50001 DROP VIEW IF EXISTS `scene_report_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `scene_report_view` (
  `scene_id` tinyint NOT NULL,
  `zone_id` tinyint NOT NULL,
  `zone_name` tinyint NOT NULL,
  `parent_zone` tinyint NOT NULL,
  `score` tinyint NOT NULL,
  `soa_email` tinyint NOT NULL,
  `soa_serialno` tinyint NOT NULL,
  `severity_level` tinyint NOT NULL,
  `severity_urgent` tinyint NOT NULL,
  `severity_high` tinyint NOT NULL,
  `severity_medium` tinyint NOT NULL,
  `severity_low` tinyint NOT NULL,
  `severity_info` tinyint NOT NULL,
  `dnssec_enabled` tinyint NOT NULL,
  `ipv6_available` tinyint NOT NULL,
  `soa_inconsistency` tinyint NOT NULL,
  `open_recursive` tinyint NOT NULL,
  `open_axfr` tinyint NOT NULL,
  `non_compliant_edns` tinyint NOT NULL,
  `server_not_working` tinyint NOT NULL,
  `rrset_inconsistency` tinyint NOT NULL,
  `number_of_servers` tinyint NOT NULL,
  `number_of_problems` tinyint NOT NULL,
  `updated_at` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `scene_user_view`
--

DROP TABLE IF EXISTS `scene_user_view`;
/*!50001 DROP VIEW IF EXISTS `scene_user_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `scene_user_view` (
  `scene_id` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `scene_name` tinyint NOT NULL,
  `creator_id` tinyint NOT NULL,
  `role` tinyint NOT NULL,
  `created_at` tinyint NOT NULL,
  `updated_at` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

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
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`scene_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene_zones`
--

DROP TABLE IF EXISTS `scene_zones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scene_zones` (
  `scene_id` int(11) NOT NULL,
  `zone_id` varchar(255) NOT NULL,
  `zone_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `contact_email` varchar(64) NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`scene_id`,`zone_id`),
  KEY `zone_id` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scenes`
--

DROP TABLE IF EXISTS `scenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `scene_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_users`
--

DROP TABLE IF EXISTS `system_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_users` (
  `account` varchar(72) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `roles_json` text NOT NULL,
  `creation_time` datetime NOT NULL,
  `modify_time` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `univ_report_view`
--

DROP TABLE IF EXISTS `univ_report_view`;
/*!50001 DROP VIEW IF EXISTS `univ_report_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `univ_report_view` (
  `zone_id` tinyint NOT NULL,
  `zone_name` tinyint NOT NULL,
  `rank` tinyint NOT NULL,
  `country_code` tinyint NOT NULL,
  `parent_zone` tinyint NOT NULL,
  `score` tinyint NOT NULL,
  `soa_email` tinyint NOT NULL,
  `soa_serialno` tinyint NOT NULL,
  `severity_level` tinyint NOT NULL,
  `severity_urgent` tinyint NOT NULL,
  `severity_high` tinyint NOT NULL,
  `severity_medium` tinyint NOT NULL,
  `severity_low` tinyint NOT NULL,
  `severity_info` tinyint NOT NULL,
  `dnssec_enabled` tinyint NOT NULL,
  `ipv6_available` tinyint NOT NULL,
  `soa_inconsistency` tinyint NOT NULL,
  `open_recursive` tinyint NOT NULL,
  `open_axfr` tinyint NOT NULL,
  `non_compliant_edns` tinyint NOT NULL,
  `server_not_working` tinyint NOT NULL,
  `rrset_inconsistency` tinyint NOT NULL,
  `number_of_servers` tinyint NOT NULL,
  `number_of_problems` tinyint NOT NULL,
  `json_report` tinyint NOT NULL,
  `remote_address` tinyint NOT NULL,
  `updated_at` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `universities`
--

DROP TABLE IF EXISTS `universities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `universities` (
  `zone_id` varchar(255) NOT NULL,
  `zone_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `country_code` varchar(24) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`zone_id`),
  KEY `country_code` (`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `role` int(11) NOT NULL DEFAULT 0,
  `nickname` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zone_users`
--

DROP TABLE IF EXISTS `zone_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zone_users` (
  `zone_id` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`zone_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `parent_idx` (`parent_zone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `scene_report_view`
--

/*!50001 DROP TABLE IF EXISTS `scene_report_view`*/;
/*!50001 DROP VIEW IF EXISTS `scene_report_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `scene_report_view` AS select `s`.`scene_id` AS `scene_id`,`s`.`zone_id` AS `zone_id`,`s`.`zone_name` AS `zone_name`,`r`.`parent_zone` AS `parent_zone`,`r`.`score` AS `score`,`r`.`soa_email` AS `soa_email`,`r`.`soa_serialno` AS `soa_serialno`,`r`.`severity_level` AS `severity_level`,`r`.`severity_urgent` AS `severity_urgent`,`r`.`severity_high` AS `severity_high`,`r`.`severity_medium` AS `severity_medium`,`r`.`severity_low` AS `severity_low`,`r`.`severity_info` AS `severity_info`,`r`.`dnssec_enabled` AS `dnssec_enabled`,`r`.`ipv6_available` AS `ipv6_available`,`r`.`soa_inconsistency` AS `soa_inconsistency`,`r`.`open_recursive` AS `open_recursive`,`r`.`open_axfr` AS `open_axfr`,`r`.`non_compliant_edns` AS `non_compliant_edns`,`r`.`server_not_working` AS `server_not_working`,`r`.`rrset_inconsistency` AS `rrset_inconsistency`,`r`.`number_of_servers` AS `number_of_servers`,`r`.`number_of_problems` AS `number_of_problems`,`r`.`updated_at` AS `updated_at` from (`scene_zones` `s` left join `reports` `r` on(`s`.`zone_id` = `r`.`zone_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `scene_user_view`
--

/*!50001 DROP TABLE IF EXISTS `scene_user_view`*/;
/*!50001 DROP VIEW IF EXISTS `scene_user_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `scene_user_view` AS select `s`.`id` AS `scene_id`,`u`.`user_id` AS `user_id`,`s`.`scene_name` AS `scene_name`,`s`.`user_id` AS `creator_id`,`u`.`role` AS `role`,`u`.`created_at` AS `created_at`,`u`.`updated_at` AS `updated_at` from (`scenes` `s` join `scene_users` `u` on(`s`.`id` = `u`.`scene_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `univ_report_view`
--

/*!50001 DROP TABLE IF EXISTS `univ_report_view`*/;
/*!50001 DROP VIEW IF EXISTS `univ_report_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `univ_report_view` AS select `u`.`zone_id` AS `zone_id`,`u`.`zone_name` AS `zone_name`,`u`.`rank` AS `rank`,`u`.`country_code` AS `country_code`,`r`.`parent_zone` AS `parent_zone`,`r`.`score` AS `score`,`r`.`soa_email` AS `soa_email`,`r`.`soa_serialno` AS `soa_serialno`,`r`.`severity_level` AS `severity_level`,`r`.`severity_urgent` AS `severity_urgent`,`r`.`severity_high` AS `severity_high`,`r`.`severity_medium` AS `severity_medium`,`r`.`severity_low` AS `severity_low`,`r`.`severity_info` AS `severity_info`,`r`.`dnssec_enabled` AS `dnssec_enabled`,`r`.`ipv6_available` AS `ipv6_available`,`r`.`soa_inconsistency` AS `soa_inconsistency`,`r`.`open_recursive` AS `open_recursive`,`r`.`open_axfr` AS `open_axfr`,`r`.`non_compliant_edns` AS `non_compliant_edns`,`r`.`server_not_working` AS `server_not_working`,`r`.`rrset_inconsistency` AS `rrset_inconsistency`,`r`.`number_of_servers` AS `number_of_servers`,`r`.`number_of_problems` AS `number_of_problems`,`r`.`json_report` AS `json_report`,`r`.`remote_address` AS `remote_address`,`r`.`updated_at` AS `updated_at` from (`universities` `u` left join `reports` `r` on(`u`.`zone_id` = `r`.`zone_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-04  9:19:10
