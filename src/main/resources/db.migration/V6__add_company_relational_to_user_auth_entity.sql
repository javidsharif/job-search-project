ALTER TABLE user_authentication
ADD COLUMN company_id BIGINT,
ADD CONSTRAINT fk_user_authentication_company_id
  FOREIGN KEY (company_id)
  REFERENCES companies(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;