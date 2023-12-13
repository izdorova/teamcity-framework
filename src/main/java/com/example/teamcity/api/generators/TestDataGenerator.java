package com.example.teamcity.api.generators;

import com.example.teamcity.api.enums.RoleEnum;
import com.example.teamcity.models.BuildType;
import com.example.teamcity.models.NewProjectDescription;
import com.example.teamcity.models.Project;
import com.example.teamcity.models.Role;
import com.example.teamcity.models.Roles;
import com.example.teamcity.models.User;

import java.util.Arrays;

public class TestDataGenerator {

    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString()+"@gmail.com")
                .roles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build())
                .build();
        var projectDescription = NewProjectDescription
                .builder()
                .parentProject(Project.builder().locator("_Root").build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();
        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(projectDescription)
                .build();

        return TestData.builder()
                .user(user)
                .project(projectDescription)
                .buildType(buildType)
                .build();

    }

    public static Roles generateRoles(RoleEnum role, String scope) {
        return Roles.builder()
                .role(Arrays.asList(Role.builder()
                        .roleId(role.getText())
                        .scope(scope)
                        .build()))
                .build();
    }
}
