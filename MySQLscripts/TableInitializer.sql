-- Create the database
CREATE DATABASE IF NOT EXISTS game_db;
USE game_db;

-- Table to store accounts (for possible multi-user support)
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    new_game BOOLEAN NOT NULL DEFAULT TRUE
);

-- Table to store characters
CREATE TABLE IF NOT EXISTS characters (
    character_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    name VARCHAR(50) NOT NULL,
    health FLOAT NOT NULL,
    total_health FLOAT NOT NULL,
    alive BOOLEAN NOT NULL DEFAULT TRUE,
    equipped_weapon VARCHAR(50),
	damage_multiplier FLOAT NOT NULL,
    total_damage FLOAT NOT NULL DEFAULT 0,
    battle_counter INT NOT NULL DEFAULT 0,
    stage_counter INT NOT NULL DEFAULT 0,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Table to store inventory for each character
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    character_id INT NOT NULL,
    item_name VARCHAR(50) NOT NULL,
	item_type VARCHAR(50) NOT NULL DEFAULT 'misc',
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (character_id) REFERENCES characters(character_id) ON DELETE CASCADE
);
