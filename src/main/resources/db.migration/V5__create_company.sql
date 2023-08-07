create TABLE IF NOT EXISTS companies (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  cv_email VARCHAR(50) NOT NULL,
  telephone VARCHAR(50),
  photo_url VARCHAR(500),
  information VARCHAR(1000),
  city VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  field_of_activity VARCHAR(500),
  foundation_date DATE,
  number_of_employees INT,
  address VARCHAR(50),
  site_of_company VARCHAR(50),
  facebook_profile_link VARCHAR(50),
  twitter_profile_link VARCHAR(50),
  linkedin_profile_link VARCHAR(50),
  instagram_profile_link VARCHAR(50),
  role_id INT,
  CONSTRAINT fk_companies_roles
        FOREIGN KEY (role_id)
        REFERENCES roles (id),
  user_authentication_id BIGINT,
  CONSTRAINT fk_company_authentication_id
        FOREIGN KEY (user_authentication_id)
        REFERENCES user_authentication(id)
  ON delete CASCADE
  ON update CASCADE
);