-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Verzió:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for operationcontrol
CREATE DATABASE IF NOT EXISTS `operationcontrol` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci */;
USE `operationcontrol`;

-- Dumping structure for tábla operationcontrol.job
CREATE TABLE IF NOT EXISTS `job` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci COMMENT='Contains information about jobs.';

-- Dumping data for table operationcontrol.job: ~0 rows (approximately)
DELETE FROM `job`;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;

-- Dumping structure for tábla operationcontrol.job_task
CREATE TABLE IF NOT EXISTS `job_task` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `JOB` int(11) NOT NULL DEFAULT 0,
  `TASK` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  KEY `FK_JOB` (`JOB`),
  KEY `FK_TASK` (`TASK`),
  CONSTRAINT `FK_JOB` FOREIGN KEY (`JOB`) REFERENCES `job` (`ID`),
  CONSTRAINT `FK_TASK` FOREIGN KEY (`TASK`) REFERENCES `task` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci COMMENT='Maps the One To Many relationship between a job and its tasks.';

-- Dumping data for table operationcontrol.job_task: ~0 rows (approximately)
DELETE FROM `job_task`;
/*!40000 ALTER TABLE `job_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_task` ENABLE KEYS */;

-- Dumping structure for tábla operationcontrol.machine
CREATE TABLE IF NOT EXISTS `machine` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `TYPE` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `WORKING` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci COMMENT='Contains information about the machines.';

-- Dumping data for table operationcontrol.machine: ~2 rows (approximately)
DELETE FROM `machine`;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
INSERT INTO `machine` (`ID`, `NAME`, `TYPE`, `WORKING`) VALUES
	(1, 'MAIN_MEASURING', '3', 0),
	(2, 'MAIN_MILLING', '1', 0);
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;

-- Dumping structure for tábla operationcontrol.task
CREATE TABLE IF NOT EXISTS `task` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `REQ_MACH_TYPE` int(11) NOT NULL,
  `EXECUTOR` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_REQUIRED_TYPE` (`REQ_MACH_TYPE`),
  KEY `FK_EXECUTOR` (`EXECUTOR`),
  CONSTRAINT `FK_EXECUTOR` FOREIGN KEY (`EXECUTOR`) REFERENCES `machine` (`ID`),
  CONSTRAINT `FK_REQUIRED_TYPE` FOREIGN KEY (`REQ_MACH_TYPE`) REFERENCES `machine_types` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci COMMENT='Contains information about tasks, that can be executed by machines.';

-- Dumping data for table operationcontrol.task: ~0 rows (approximately)
DELETE FROM `task`;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

-- Dumping structure for tábla operationcontrol.worker
CREATE TABLE IF NOT EXISTS `worker` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `CURRENT_JOB` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CURRENT_JOB` (`CURRENT_JOB`),
  CONSTRAINT `FK_CURRENT_JOB` FOREIGN KEY (`CURRENT_JOB`) REFERENCES `job` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci COMMENT='Contains information about workers';

-- Dumping data for table operationcontrol.worker: ~0 rows (approximately)
DELETE FROM `worker`;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
