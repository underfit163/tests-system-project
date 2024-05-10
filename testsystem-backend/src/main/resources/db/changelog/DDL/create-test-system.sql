-- liquibase formatted sql
--changeset underfit:1
CREATE TABLE IF NOT EXISTS "test" (
                                      "test_id" bigserial,
                                      "test_name" varchar NOT NULL,
                                      "description" text,
                                      PRIMARY KEY ("test_id")
);

CREATE TABLE IF NOT EXISTS "question" (
                                          "question_id" bigserial,
                                          "question_text" text NOT NULL,
                                          "test_id" bigint,
                                          PRIMARY KEY ("question_id")
);

CREATE TABLE IF NOT EXISTS "option" (
                                        "option_id" bigserial,
                                        "option_text" text NOT NULL,
                                        "score" int NOT NULL,
                                        "question_id" bigint,
                                        PRIMARY KEY ("option_id")
);

CREATE TABLE IF NOT EXISTS "result" (
                                        "result_id" bigserial,
                                        "score" int NOT NULL,
                                        "accept_result" bool,
                                        "test_id" bigint,
                                        "user_id" bigint,
                                        PRIMARY KEY ("result_id")
);

CREATE TABLE IF NOT EXISTS "users" (
                                      "user_id" bigserial,
                                      "name" varchar NOT NULL,
                                      "login" varchar NOT NULL UNIQUE,
                                      "password" varchar NOT NULL,
                                      "role" smallint NOT NULL,
                                      "email" varchar UNIQUE,
                                      "birthday" date NOT NULL,
                                      "start_work" date NOT NULL,
                                      "work_number" bigint,
                                      PRIMARY KEY ("user_id")
);

CREATE TABLE IF NOT EXISTS "assessment" (
                                            "assessment_id" bigserial,
                                            "assessment_name" text NOT NULL,
                                            "min_score" int,
                                            "max_score" int,
                                            "result_description" text,
                                            "test_id" bigint,
                                            PRIMARY KEY ("assessment_id")
);

CREATE TABLE IF NOT EXISTS "answer" (
                                        "answer_id" bigserial,
                                        "result_id" bigint,
                                        "option_id" bigint,
                                        PRIMARY KEY ("answer_id")
);

CREATE TABLE IF NOT EXISTS "ticket" (
                                        "ticket_id" bigserial,
                                        "color" varchar NOT NULL,
                                        "date_withdrawal" date,
                                        "reason_withdrawal" varchar,
                                        "user_id" bigint,
                                        PRIMARY KEY ("ticket_id")
);

ALTER TABLE "question" ADD CONSTRAINT "question_fk2" FOREIGN KEY ("test_id") REFERENCES "test"("test_id");
ALTER TABLE "option" ADD CONSTRAINT "option_fk3" FOREIGN KEY ("question_id") REFERENCES "question"("question_id");
ALTER TABLE "result" ADD CONSTRAINT "result_fk2" FOREIGN KEY ("test_id") REFERENCES "test"("test_id");

ALTER TABLE "result" ADD CONSTRAINT "result_fk3" FOREIGN KEY ("user_id") REFERENCES "users"("user_id");

ALTER TABLE "assessment" ADD CONSTRAINT "assessment_fk5" FOREIGN KEY ("test_id") REFERENCES "test"("test_id");
ALTER TABLE "answer" ADD CONSTRAINT "answer_fk1" FOREIGN KEY ("result_id") REFERENCES "result"("result_id");

ALTER TABLE "answer" ADD CONSTRAINT "answer_fk2" FOREIGN KEY ("option_id") REFERENCES "option"("option_id");
ALTER TABLE "ticket" ADD CONSTRAINT "ticket_fk4" FOREIGN KEY ("user_id") REFERENCES "users"("user_id");