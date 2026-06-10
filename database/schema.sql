-- ========================================================
-- 1. DATABASE RESET & CLEANUP
-- ========================================================
DROP DATABASE IF EXISTS astronomy_system;
CREATE DATABASE astronomy_system;
USE astronomy_system;

-- ========================================================
-- 2. SCHEMA CREATION
-- ========================================================
CREATE TABLE observers (
    observer_id INT PRIMARY KEY,
    observer_name VARCHAR(100),
    experience_level VARCHAR(50),
    location VARCHAR(100)
);

CREATE TABLE telescopes (
    telescope_id INT PRIMARY KEY,
    model VARCHAR(100),
    max_magnification INT,
    aperture_size DOUBLE,
    motorized BOOLEAN
);

CREATE TABLE celestial_objects (
    name VARCHAR(100) PRIMARY KEY,
    light_years INT,
    magnitude DOUBLE,
    right_ascension INT,
    declination INT,
    constellation VARCHAR(100),
    object_type VARCHAR(50),
    
    spectral_class VARCHAR(10),
    is_nebula BOOLEAN,
    
    number_of_moons INT,
    has_rings BOOLEAN,
    
    galaxy_type VARCHAR(50),
    estimated_stars INT
);

CREATE TABLE observation_sessions (
    session_id INT PRIMARY KEY,
    observer_id INT,
    telescope_id INT,
    object_name VARCHAR(100),
    date VARCHAR(50),
    start_hour INT,
    duration_minutes INT,
    notes TEXT,
    status VARCHAR(20),
    fail_reason VARCHAR(255),

    FOREIGN KEY (observer_id) REFERENCES observers(observer_id),
    FOREIGN KEY (telescope_id) REFERENCES telescopes(telescope_id),
    FOREIGN KEY (object_name) REFERENCES celestial_objects(name)
);

-- ========================================================
-- 3. EXPANDED & PAKISTANI LOCALIZED DEMO DATA
-- ========================================================

-- Observers (All Pakistani Names, mixed experience and institutions)
INSERT INTO observers (observer_id, observer_name, experience_level, location) VALUES
(1, 'Dr. Zain-ul-Abideen', 'Expert', 'Lahore, Pakistan'),
(2, 'Ayesha Siddiqua', 'Intermediate', 'Karachi, Pakistan'),
(3, 'Muhammad Ali Hassan', 'Beginner', 'Islamabad, Pakistan'),
(4, 'Prof. Maryam Niaz', 'Expert', 'Lahore, Pakistan'),
(5, 'Hamza Tariq', 'Intermediate', 'Peshawar, Pakistan'),
(6, 'Amna Bilal', 'Beginner', 'Multan, Pakistan');

-- Telescopes (Expanded inventory)
INSERT INTO telescopes (telescope_id, model, max_magnification, aperture_size, motorized) VALUES
(1, 'Celestron NexStar 8SE', 400, 203.2, true),
(2, 'Orion SkyQuest XT10', 300, 254.0, false),
(3, 'Meade LX90 12-inch', 500, 304.8, true),
(4, 'Sky-Watcher Classic 200P', 240, 203.0, false),
(5, 'William Optics Zenithstar 73', 150, 73.0, true);

-- Celestial Objects (Scientific attributes mapped perfectly with realistic values)
INSERT INTO celestial_objects (name, light_years, magnitude, right_ascension, declination, constellation, object_type, spectral_class, is_nebula, number_of_moons, has_rings, galaxy_type, estimated_stars) VALUES
-- Stars
('Sirius', 9, -1.46, 101, -17, 'Canis Major', 'Star', 'A1V', false, null, null, null, null),
('Betelgeuse', 700, 0.42, 88, 7, 'Orion', 'Star', 'M2Iab', false, null, null, null, null),
('Vega', 25, 0.03, 279, 39, 'Lyra', 'Star', 'A0V', false, null, null, null, null),
('Polaris', 433, 1.97, 38, 89, 'Ursa Minor', 'Star', 'F7Ib', false, null, null, null, null),

-- Planets
('Jupiter', 1, -2.94, 196, -5, 'Virgo', 'Planet', null, null, 95, true, null, null),
('Saturn', 1, 0.68, 315, -18, 'Capricornus', 'Planet', null, null, 146, true, null, null),
('Mars', 1, -2.91, 127, 24, 'Gemini', 'Planet', null, null, 2, false, null, null),
('Venus', 1, -4.60, 45, 12, 'Taurus', 'Planet', null, null, 0, false, null, null),

-- Galaxies
('Andromeda Galaxy', 2537000, 3.44, 10, 41, 'Andromeda', 'Galaxy', null, null, null, null, 'Spiral', 1000000000),
('Milky Way Core', 26000, 0.0, 266, -29, 'Sagittarius', 'Galaxy', null, null, null, null, 'Barred Spiral', 400000000),
('Triangulum Galaxy', 2730000, 5.72, 23, 30, 'Triangulum', 'Galaxy', null, null, null, null, 'Spiral', 40000000),
('Sombrero Galaxy', 2800000, 8.00, 192, -11, 'Virgo', 'Galaxy', null, null, null, null, 'Unbarred Spiral', 80000000),

-- Deep Sky Nebulae
('Orion Nebula', 1344, 4.0, 84, -5, 'Orion', 'Star', 'O6', true, null, null, null, null),
('Crab Nebula', 6500, 8.4, 83, 22, 'Taurus', 'Star', 'Unknown', true, null, null, null, null);

-- Observation Sessions (12 entries showcasing deep application logic and multi-table relationships)
INSERT INTO observation_sessions (session_id, observer_id, telescope_id, object_name, date, start_hour, duration_minutes, notes, status, fail_reason) VALUES
-- Successful Tracks
(1, 1, 1, 'Sirius', '2026-05-10', 21, 90, 'Clear skies in Lahore, excellent atmospheric seeing conditions.', 'SUCCESS', ''),
(2, 2, 2, 'Jupiter', '2026-05-12', 22, 120, 'Great detail on atmospheric cloud bands from Karachi coastline.', 'SUCCESS', ''),
(3, 4, 3, 'Betelgeuse', '2026-05-18', 20, 150, 'Long exposure capture monitoring alpha-star variations.', 'SUCCESS', ''),
(4, 2, 4, 'Saturn', '2026-05-20', 21, 180, 'Excellent ring tilt clarity via manual Dobson tracking.', 'SUCCESS', ''),
(5, 5, 5, 'Venus', '2026-05-25', 19, 45, 'Perfect transit alignment tracked flawlessly using GoTo motor.', 'SUCCESS', ''),
(6, 1, 3, 'Milky Way Core', '2026-05-28', 01, 240, 'Astrophotography deep stack run completed without trailing.', 'SUCCESS', ''),
(7, 4, 5, 'Polaris', '2026-06-01', 21, 60, 'Calibrating mount orientation based on North Star baseline.', 'SUCCESS', ''),

-- Failed Sessions (Simulating distinct application system business constraints)
-- Constraint 1: Motorized Tracking required for complex objects by Beginners
(8, 3, 2, 'Andromeda Galaxy', '2026-05-15', 23, 60, 'Attempted manually tracking deep sky coordinates.', 'FAILED', 'Beginner observers require a Motorized Telescope to track high-complexity Galaxy structures.'),
(9, 6, 4, 'Sombrero Galaxy', '2026-06-04', 22, 40, 'Target lost instantly due to manual alignment drifting.', 'FAILED', 'Beginner observers require a Motorized Telescope to track high-complexity Galaxy structures.'),

-- Constraint 2: Weather disruption
(10, 1, 3, 'Orion Nebula', '2026-05-22', 22, 15, 'Session aborted due to sudden dust storm and heavy cloud cover.', 'FAILED', 'Weather Disruption: Localized cloud cover reduced visibility below acceptable threshold.'),
(11, 5, 2, 'Crab Nebula', '2026-06-05', 23, 10, 'High relative humidity and smog forced immediate lens cap safety coverage.', 'FAILED', 'Weather Disruption: High humidity risk detected on optical elements.'),

-- Constraint 3: Hardware Magnification limitations
(12, 3, 4, 'Vega', '2026-06-02', 19, 30, 'Attempted to resolve close binary component under high zoom.', 'FAILED', 'Hardware Limitation: Requested magnification exceeds maximum threshold for selected equipment.');