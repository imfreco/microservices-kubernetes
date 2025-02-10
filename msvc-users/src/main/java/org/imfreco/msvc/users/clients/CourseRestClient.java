package org.imfreco.msvc.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "host.docker.internal:8002/courses")
public interface CourseRestClient {
    @DeleteMapping("/course-user/{userId}")
    void deleteCourseUserByUserId(@PathVariable Long userId);
}
