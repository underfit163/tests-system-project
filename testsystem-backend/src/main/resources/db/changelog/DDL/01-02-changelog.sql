-- liquibase formatted sql
--changeset underfit:2
ALTER TABLE "question" drop constraint "question_fk2";
ALTER TABLE "option" drop constraint "option_fk3";
ALTER TABLE "result" drop constraint "result_fk2";
ALTER TABLE "result" drop constraint "result_fk3";
ALTER TABLE "assessment" drop constraint "assessment_fk5";
ALTER TABLE "answer" drop constraint "answer_fk1";
ALTER TABLE "answer" drop constraint "answer_fk2";
ALTER TABLE "ticket" drop constraint "ticket_fk4";

ALTER TABLE "question" ADD CONSTRAINT "question_fk2" FOREIGN KEY ("test_id") REFERENCES "test"("test_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "option" ADD CONSTRAINT "option_fk3" FOREIGN KEY ("question_id") REFERENCES "question"("question_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "result" ADD CONSTRAINT "result_fk2" FOREIGN KEY ("test_id") REFERENCES "test"("test_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "result" ADD CONSTRAINT "result_fk3" FOREIGN KEY ("user_id") REFERENCES "users"("user_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "assessment" ADD CONSTRAINT "assessment_fk5" FOREIGN KEY ("test_id") REFERENCES "test"("test_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "answer" ADD CONSTRAINT "answer_fk1" FOREIGN KEY ("result_id") REFERENCES "result"("result_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "answer" ADD CONSTRAINT "answer_fk2" FOREIGN KEY ("option_id") REFERENCES "option"("option_id") on DELETE cascade on UPDATE cascade;
ALTER TABLE "ticket" ADD CONSTRAINT "ticket_fk4" FOREIGN KEY ("user_id") REFERENCES "users"("user_id") on DELETE cascade on UPDATE cascade;