-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:8889
-- Generation Time: Dec 18, 2015 at 12:35 PM
-- Server version: 5.5.38
-- PHP Version: 5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `InventoryControlPTL`
--

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_hash` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `permission` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`username`, `email`, `firstname`, `lastname`, `password_hash`, `permission`) VALUES
('admin', 'bla@gmail.com', 'Michael', 'Matthias', '$2a$10$fcwb84MwAdAax3Nitn3lN.C8YUKKmVHDoEIYmo2MUg6n1.0XR89c2', 0),
('helmut', 'bla@gmail.com', 'Helmut', '', '$2a$10$SVyK92t2FfyK01nX.AKXR.1FFjKFO1qPuDW4lH6vr5hx3MUdR/xUm', 1),
('nelly', 'bla@gmail.com', 'Nelly', 'Pellissier', '$2a$10$/C37Oveso7VvBC5NlrIx5uZ45jNs5dAjEM.kc1.9aHrdtrNvDNSRS', 1),
('rene', 'rene@ptl-logistic.com', 'Rene', 'Pellissier', '$2a$10$e/9GCDW6osjSIUcEizGx5e3PQahBHHVWHqlf/qWARgqkubM1F.np.', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `User`
--
ALTER TABLE `User`
 ADD PRIMARY KEY (`username`);
