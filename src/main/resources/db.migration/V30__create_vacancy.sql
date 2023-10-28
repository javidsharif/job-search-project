CREATE TABLE IF NOT EXISTS vacancies (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  applicant_name VARCHAR(30),
  telephone VARCHAR(30),
  email VARCHAR(50),
  vacancy_name VARCHAR(50) NOT NULL,
  is_internship BOOLEAN,
  address VARCHAR(20),
  from_salary DECIMAL,
  to_salary DECIMAL,
  expiry_date DATE,
  text_of_vacancy VARCHAR(500),
  special_requirements VARCHAR(500),
  for_apply VARCHAR(50),
  minimal_work_experience INT,
  note_contact_at_vacancy BOOLEAN,
  post_vacancy BOOLEAN,
  created_at DATE NOT NULL DEFAULT CURRENT_DATE,
  view_number BIGINT,
  communication_tool_id INT,
    CONSTRAINT fk_vacancies_communication_tools
          FOREIGN KEY (communication_tool_id)
          REFERENCES communication_tools (id),
  category_id INT,
    CONSTRAINT fk_vacancies_categories
            FOREIGN KEY (category_id)
            REFERENCES categories (id),
--   city_id INT,
--     CONSTRAINT fk_vacancies_cities
--               FOREIGN KEY (city_id)
--               REFERENCES cities (id),
  currency_id INT,
    CONSTRAINT fk_vacancies_currencies
              FOREIGN KEY (currency_id)
              REFERENCES currencies (id),
--   job_type_id INT,
--       CONSTRAINT fk_vacancies_job_types
--                 FOREIGN KEY (job_type_id)
--                 REFERENCES job_types (id),
--   special_knowledge_id INT,
--       CONSTRAINT fk_vacancies_special_knowledge
--                 FOREIGN KEY (special_knowledge_id)
--                 REFERENCES special_knowledge (id),
  education_level_id INT,
        CONSTRAINT fk_vacancies_education_levels
                  FOREIGN KEY (education_level_id)
                  REFERENCES education_levels (id),
  status_id INT,
        CONSTRAINT fk_vacancies_statuses
                  FOREIGN KEY (status_id)
                  REFERENCES statuses (id),
  note_id BIGINT,
          CONSTRAINT fk_vacancies_notes
                    FOREIGN KEY (note_id)
                    REFERENCES notes (id),
  company_id INT,
          CONSTRAINT fk_vacancies_companies
                    FOREIGN KEY (company_id)
                    REFERENCES companies (id)
);