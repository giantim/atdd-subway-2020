package wooteco.subway.maps.map.documentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;
import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {
    @Autowired
    private MapController mapController;

    @MockBean
    private MapService mapService;

    private TokenResponse tokenResponse;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @DisplayName("로그인 하지 않은 상태에서 경로 조회 문서화")
    @Test
    void findPath() {
        PathResponse pathResponse = new PathResponse(
                Arrays.asList(
                        new StationResponse(1L, "a", LocalDateTime.now(), LocalDateTime.now()),
                        new StationResponse(2L, "b", LocalDateTime.now(), LocalDateTime.now())
                ),
                10,
                5
        );
        Map<String, Object> params = new HashMap<>();
        params.put("source", 1L);
        params.put("target", 2L);
        params.put("type", PathType.DURATION);
        when(mapService.findPath(any(), anyLong(), anyLong(), any())).thenReturn(pathResponse);

        given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                params(params).
                when().
                get("/paths").
                then().
                log().all().
                apply(document("paths/non-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("source").description("출발역 아이디"),
                                parameterWithName("target").description("도착역 아이디"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("stations").type(JsonFieldType.ARRAY).description("경로에 포함된 지하철 목록"),
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("지하철역 이름"),
                                fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("총 거리"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")
                        ))).
                extract();
    }

    @DisplayName("로그인 한 상태에서 경로 조회 문서화")
    @Test
    void findPathWithLogin() {
        PathResponse pathResponse = new PathResponse(
                Arrays.asList(
                        new StationResponse(1L, "a", LocalDateTime.now(), LocalDateTime.now()),
                        new StationResponse(2L, "b", LocalDateTime.now(), LocalDateTime.now())
                ),
                10,
                5
        );
        Map<String, Object> params = new HashMap<>();
        params.put("source", 1L);
        params.put("target", 2L);
        params.put("type", PathType.DURATION);
        when(mapService.findPath(any(), anyLong(), anyLong(), any())).thenReturn(pathResponse);

        given().log().all().
                header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                params(params).
                when().
                get("/paths").
                then().
                log().all().
                apply(document("paths/login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer auth credentials")),
                        requestParameters(
                                parameterWithName("source").description("출발역 아이디"),
                                parameterWithName("target").description("도착역 아이디"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("stations").type(JsonFieldType.ARRAY).description("경로에 포함된 지하철 목록"),
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("지하철역 이름"),
                                fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("총 거리"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")
                        ))).
                extract();
    }
}
