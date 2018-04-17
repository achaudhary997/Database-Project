-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 17, 2018 at 06:00 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gym_dbms`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `MembershipType` varchar(20) NOT NULL,
  `MembershipFee` int(11) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  KEY `MembershipID` (`MembershipType`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CustomerID`, `Name`, `MembershipType`, `MembershipFee`) VALUES
(1, 'Deepak', 'Flex', 7500),
(2, 'Anubhav', 'Saver', 5000),
(3, 'Yashit', 'Premium', 10000),
(4, 'Piyush', 'Flex', 7500),
(5, 'Maiti', 'Saver', 5000),
(6, 'Dank', 'Premium', 10000),
(7, 'Diff', 'Saver', 5000);

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
CREATE TABLE IF NOT EXISTS `equipment` (
  `machineID` int(11) NOT NULL AUTO_INCREMENT,
  `lastMaintenance` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `machineName` varchar(30) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '1',
  `workingProperly` tinyint(1) DEFAULT '1',
  `MaintenanceCost` int(11) NOT NULL,
  PRIMARY KEY (`machineID`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`machineID`, `lastMaintenance`, `machineName`, `quantity`, `workingProperly`, `MaintenanceCost`) VALUES
(1, '2018-04-07 09:16:00', 'Treadmill', 5, 1, 40000),
(2, '2018-04-07 09:16:22', 'Cycle', 10, 1, 300000),
(3, '2018-04-07 09:17:19', 'Abdominal Bench', 1, 1, 20000),
(4, '2018-04-07 09:17:19', 'Bench Press', 2, 1, 50000),
(5, '2018-04-07 09:17:55', 'Dipping Bars', 3, 1, 45000),
(6, '2018-04-07 09:18:13', 'Foam Roller', 5, 1, 25000);

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `ExpenseID` int(11) NOT NULL AUTO_INCREMENT,
  `Purpose` varchar(30) NOT NULL,
  `Amount` decimal(10,0) NOT NULL,
  `Type` varchar(20) NOT NULL,
  PRIMARY KEY (`ExpenseID`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`ExpenseID`, `Purpose`, `Amount`, `Type`) VALUES
(1, 'Salary', '5000000', 'debit'),
(2, 'Salary', '100000', 'debit'),
(3, 'Salary', '5000000', 'debit'),
(4, 'Maintenance', '40000', 'debit'),
(5, 'Maintenance', '300000', 'debit'),
(6, 'Salary', '100000', 'debit'),
(7, 'Maintenance', '20000', 'debit'),
(8, 'Maintenance', '20000', 'debit'),
(9, 'Salary', '5000000', 'debit'),
(10, 'Maintenance', '20000', 'debit');

-- --------------------------------------------------------

--
-- Table structure for table `membership_plans`
--

DROP TABLE IF EXISTS `membership_plans`;
CREATE TABLE IF NOT EXISTS `membership_plans` (
  `Cost` decimal(10,0) NOT NULL,
  `Duration` int(11) NOT NULL,
  `Trainer` varchar(20) NOT NULL,
  `MembershipType` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `membership_plans`
--

INSERT INTO `membership_plans` (`Cost`, `Duration`, `Trainer`, `MembershipType`) VALUES
('5000', 5, 'Jackie Chan', 'Saver'),
('10000', 5, 'Bruce Li', 'Premium'),
('7500', 5, 'Will Smith', 'Flex'),
('2000', 5, 'Mukesh Ambani', 'Suicide');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
  `employeeID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Division` varchar(30) NOT NULL,
  `Salary` decimal(10,0) NOT NULL,
  PRIMARY KEY (`employeeID`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`employeeID`, `Name`, `Division`, `Salary`) VALUES
(1, 'Jackie Chan', 'Trainer', '5000000'),
(2, 'Bruce Li', 'Trainer', '5500000'),
(3, 'Will Smith', 'Trainer', '6000000'),
(4, 'Laka', 'Maintenance', '100000'),
(5, 'Bisht', 'Maintenance', '100000');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
