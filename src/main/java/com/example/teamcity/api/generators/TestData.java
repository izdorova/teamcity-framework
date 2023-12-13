package com.example.teamcity.api.generators;

import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedProjectRequest;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import com.example.teamcity.api.specifications.Specifications;
import com.example.teamcity.models.BuildType;
import com.example.teamcity.models.NewProjectDescription;
import com.example.teamcity.models.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        var spec = Specifications.getSpec().authSpec(user);
        new UncheckedProjectRequest(spec).delete(project.getId());
        new UncheckedUser(spec).delete(user.getUsername());
        //new UncheckedBuildConfig(spec).delete(buildType.getId());
    }
}
