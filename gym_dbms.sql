-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 07, 2018 at 06:30 AM
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
  `MembershipID` int(11) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  KEY `MembershipID` (`MembershipID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
  PRIMARY KEY (`machineID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`machineID`, `lastMaintenance`, `machineName`, `quantity`, `workingProperly`) VALUES
(1, '2018-04-07 05:50:55', 'Treadmill', 1, 1),
(2, '2018-04-07 06:22:26', 'Bench Press', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `ExpenseID` int(11) NOT NULL AUTO_INCREMENT,
  `Purpose` varchar(30) NOT NULL,
  `Amount` decimal(10,0) NOT NULL,
  PRIMARY KEY (`ExpenseID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `membership_plans`
--

DROP TABLE IF EXISTS `membership_plans`;
CREATE TABLE IF NOT EXISTS `membership_plans` (
  `membershipID` int(11) NOT NULL AUTO_INCREMENT,
  `Cost` decimal(10,0) NOT NULL,
  `Duration` date NOT NULL,
  PRIMARY KEY (`membershipID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`employeeID`, `Name`, `Division`, `Salary`) VALUES
(1, 'Raghav Sharma', 'Trainer', '20000');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
