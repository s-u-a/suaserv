CREATE TABLE t_exchange_rate (
	c_date DATE PRIMARY KEY,
	c_carbon REAL,
	c_aluminium REAL,
	c_wolfram REAL,
	c_radium REAL,
	c_tritium REAL
);

CREATE TABLE t_fleets (
	c_fleet_id VARCHAR(32) PRIMARY KEY,
	c_targets TEXT,
	finished TEXT
);

CREATE TABLE t_fleets_targets (
	c_fleet_id VARCHAR(32),
	c_galaxy SMALLINT,
	c_system SMALLINT,
	c_planet SMALLINT,
	c_type SMALLINT,
	c_cost_tritium BIGINT
);

CREATE TABLE t_fleets_users (
	c_fleet_id VARCHAR(32),
	c_user_id VARCHAR(32),
	c_leader BOOLEAN,
	c_from_galaxy SMALLINT,
	c_from_system SMALLINT,
	c_from_planet SMALLINT,
	c_start TIMESTAMP,
	c_items TEXT
);

CREATE TABLE t_users (
	c_user_id VARCHAR(32) PRIMARY KEY,
	c_username VARCHAR(32) UNIQUE,
	c_password VARCHAR(32),
	c_alliance VARCHAR(32),
	c_email VARCHAR(64),
	c_registration TIMESTAMP,
	c_last_activity TIMESTAMP,
	c_messages_receive INTEGER,
	c_messenger_type SMALLINT,
	c_messenger_uname VARCHAR(64),
	c_messenger_receive INTEGER,
	c_locked TIMESTAMP,
	c_holidays TIMESTAMP,
	c_scores_buildings BIGINT,
	c_scores_research BIGINT,
	c_scores_robots BIGINT,
	c_scores_fleet BIGINT,
	c_scores_defence BIGINT,
	c_scores_flight_exp BIGINT,
	c_scores_fighting_exp BIGINT,
	c_sress_carbon BIGINT,
	c_sress_aluminium BIGINT,
	c_sress_wolfram BIGINT,
	c_sress_radium BIGINT,
	c_sress_tritium BIGINT,
	c_items_research TEXT,
	c_description TEXT,
	c_description_html TEXT,
	c_agent_settings TEXT
);

CREATE TABLE t_planets (
	c_galaxy SMALLINT,
	c_system SMALLINT,
	c_planet SMALLINT,
	c_original_size SMALLINT,
	c_occupied BOOLEAN,
	c_user_id VARCHAR(32),
	c_planet_name VARCHAR(32),
	c_ress_carbon BIGINT,
	c_ress_aluminium BIGINT,
	c_ress_wolfram BIGINT,
	c_ress_radium BIGINT,
	c_ress_tritium BIGINT,
	c_last_ress_refresh TIMESTAMP,
	c_wreckages_carbon BIGINT,
	c_wreckages_aluminium BIGINT,
	c_wreckages_wolfram BIGINT,
	c_wreckages_radium BIGINT,
	c_wreckages_tritium BIGINT,
	c_total_size INTEGER,
	c_used_size INTEGER,
	c_prod_factors TEXT,
	c_items_buildings TEXT,
	c_items_robots TEXT,
	c_items_fleet TEXT,
	c_items_defence TEXT
);

CREATE TABLE t_building_items (
	c_id BIGINT PRIMARY KEY,
	c_galaxy SMALLINT,
	c_system SMALLINT,
	c_planet SMALLINT,
	c_item_id VARCHAR(32),
	c_next_finishing_time TIMESTAMP,
	c_duration INTERVAL,
	c_number BIGINT,
	c_cost_carbon BIGINT,
	c_cost_aluminium BIGINT,
	c_cost_wolfram BIGINT,
	c_cost_radium BIGINT
);

CREATE TABLE t_friendship_requests (
	c_user_id_from VARCHAR(32),
	c_user_id_to VARCHAR(32)
);

CREATE TABLE t_alliances (
	c_alliance_id VARCHAR(32),
	c_tag VARCHAR(32),
	c_name VARCHAR(32),
	c_allow_requests BOOLEAN,
	c_public_description TEXT,
	c_public_description_html TEXT,
	c_private_description TEXT,
	c_private_description_html TEXT
);

CREATE TABLE t_alliances_users (
	c_alliance_id VARCHAR(32),
	c_user_id VARCHAR(32),
	c_rank VARCHAR(32),
	c_entering_time TIMESTAMP,
	c_permissions SMALLINT
);

CREATE TABLE t_alliance_requests (
	c_user_id_from VARCHAR(32),
	c_alliance_to VARCHAR(32)
);

CREATE TABLE t_messages (
	c_message_id VARCHAR(32) PRIMARY KEY,
	c_time TIMESTAMP,
	c_sender_id VARCHAR(32),
	c_subject VARCHAR(64),
	c_html BOOLEAN,
	c_text TEXT,
	c_text_html TEXT
);

CREATE TABLE t_messages_users (
	c_message_id VARCHAR(32),
	c_user_id VARCHAR(32),
	c_type SMALLINT,
	c_read BOOLEAN,
	c_archived BOOLEAN
);

CREATE TABLE t_public_messages (
	c_message_id VARCHAR(32),
	c_last_view TIMESTAMP,
	c_sender_name VARCHAR(32),
	c_receiver_name VARCHAR(32),
	c_time TIMESTAMP,
	c_html BOOLEAN,
	c_type SMALLINT,
	c_text TEXT,
	c_text_html TEXT
);
