package ch.uzh.ciclassifier.webserver.controller;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.webserver.model.ExtractRequest;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtractorController {

    @PostMapping("/extract")
    JSONObject extract(@RequestBody ExtractRequest request) {

        String target = request.getTarget();
        try {
            Evaluation evaluation;

            if (request.getTarget().startsWith("https://github.com")) {
                evaluation = Evaluation.createFromGitUrl(target);
            } else {
                evaluation = Evaluation.createFromConfiguration(target);
            }
            evaluation.evaluate();

            JSONObject result = evaluation.toJson();
            result.put("status", "success");

            return result;

        } catch (Exception e) {
            JSONObject failed = new JSONObject();
            failed.put("status", "failed");
            failed.put("reason", e.getMessage());

            return failed;
        }
    }
}