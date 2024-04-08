-- Create companies table
CREATE TABLE companies (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           price DOUBLE PRECISION NOT NULL
);

-- Create transactions table
CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              instant TIMESTAMP NOT NULL,
                              price DOUBLE PRECISION NOT NULL,
                              company_id INTEGER NOT NULL,
                              FOREIGN KEY (company_id) REFERENCES companies(id)
);
