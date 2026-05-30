CREATE DATABASE astronomy_system;
USE astronomy_system;
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

    FOREIGN KEY (observer_id)
        REFERENCES observers(observer_id),

    FOREIGN KEY (telescope_id)
        REFERENCES telescopes(telescope_id)
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
ALTER TABLE observation_sessions 
ADD CONSTRAINT fk_session_target
FOREIGN KEY (object_name) REFERENCES celestial_objects(name);

SET SQL_SAFE_UPDATES = 0;
