-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2021 at 06:44 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(255) NOT NULL,
  `pseudo` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `pseudo`, `password`) VALUES
(1, 'git', 'ttic');

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE `compte` (
  `id` int(255) NOT NULL,
  `numero` int(15) NOT NULL,
  `titulaire` varchar(255) NOT NULL,
  `solde` int(255) NOT NULL,
  `debitmax` int(255) NOT NULL,
  `decouvert` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `compte`
--

INSERT INTO `compte` (`id`, `numero`, `titulaire`, `solde`, `debitmax`, `decouvert`) VALUES
(22, 43775672, 'eriksen', 126000, 3750, 750),
(23, 92005391, 'ivan perisic', 1017500, 430000, 86000),
(24, 55074007, 'lukaku', 8785300, 500000, 9000000),
(25, 76100538, 'david alaba', 104000, 4000, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(255) NOT NULL,
  `montant` int(255) NOT NULL,
  `statut` text NOT NULL,
  `destinataire` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `compteid` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `montant`, `statut`, `destinataire`, `type`, `compteid`) VALUES
(1, 9500, 'reussie', 'eriksen', 'crediter', 22),
(2, 100000, 'reussie', 'eriksen', 'virement', 24),
(3, 109500, 'reussie', 'eriksen', 'crediter', 22),
(4, 1010000, 'reussie', 'ivan perisic', 'crediter', 23),
(5, 0, 'reussie', 'lukaku', 'modification', 24),
(6, 100000, 'reussie', 'david alaba', 'virement', 24),
(7, 118000, 'reussie', 'david alaba', 'crediter', 25),
(8, 10000, 'echec', 'david alaba', 'virement', 22),
(9, 2000, 'reussie', 'david alaba', 'virement', 22),
(10, 120000, 'reussie', 'david alaba', 'crediter', 25),
(11, 1015000, 'reussie', 'ivan perisic', 'crediter', 23),
(12, 8000, 'reussie', 'eriksen', 'virement', 25),
(13, 115500, 'reussie', 'eriksen', 'crediter', 22),
(14, 8000, 'reussie', 'eriksen', 'virement', 25),
(15, 123500, 'reussie', 'eriksen', 'crediter', 22),
(17, 126000, 'reussie', 'eriksen', 'crediter', 22),
(20, 1017500, 'reussie', 'ivan perisic', 'crediter', 23),
(23, 8800300, 'reussie', 'lukaku', 'crediter', 24),
(24, 0, 'reussie', 'david alaba', 'modification', 25),
(25, 15000, 'reussie', 'aguero', 'virement', 24),
(27, 0, 'reussie', 'david alaba', 'modification', 25),
(28, 0, 'reussie', 'david alaba', 'modification', 25);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
