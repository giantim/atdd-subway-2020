package wooteco.subway.maps.map.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.members.member.domain.LoginMember;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PathResponseAssemblerTest {
    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "a";

    private Line line;

    @BeforeEach
    private void setUp() {
        line = TestObjectUtils.createLine(4L, "4호선", "BLUE", 0);
    }

    @DisplayName("10km 이하일 때 요금 계산")
    @Test
    void calculateFareUnder10kmTest() {
        LineStation lineStation1 = new LineStation(2L, 1L, 1, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 5, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(null, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(1_250);
    }

    @DisplayName("10km 초과 50km 이하일 때 요금 계산")
    @Test
    void calculateFareOver10kmUnder50kmTest() {
        LineStation lineStation1 = new LineStation(2L, 1L, 30, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 14, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(null, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(1_950);
    }

    @DisplayName("50km 초과일 때 요금 계산")
    @Test
    void calculateFareOver50kmTest() {
        LineStation lineStation1 = new LineStation(2L, 1L, 30, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 64, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(null, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(2_850);
    }

    @DisplayName("성인의 요금 계산")
    @Test
    void calculateFareAdultTest() {
        LoginMember loginMember = new LoginMember(MEMBER_ID, EMAIL, PASSWORD, 20);
        LineStation lineStation1 = new LineStation(2L, 1L, 1, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 5, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(loginMember, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(1_250);
    }

    @DisplayName("청소년의 요금 계산")
    @Test
    void calculateFareYouthTest() {
        LoginMember loginMember = new LoginMember(MEMBER_ID, EMAIL, PASSWORD, 16);
        LineStation lineStation1 = new LineStation(2L, 1L, 1, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 5, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(loginMember, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(720);
    }

    @DisplayName("어린이의 요금 계산")
    @Test
    void calculateFareChildrenTest() {
        LoginMember loginMember = new LoginMember(MEMBER_ID, EMAIL, PASSWORD, 12);
        LineStation lineStation1 = new LineStation(2L, 1L, 1, 1);
        LineStation lineStation2 = new LineStation(3L, 2L, 5, 5);
        List<LineStationEdge> lineStationEdges = Arrays.asList(
                new LineStationEdge(lineStation1, line.getId()),
                new LineStationEdge(lineStation2, line.getId())
        );
        SubwayPath subwayPath = new SubwayPath(lineStationEdges);
        Map<Long, Station> stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));

        PathResponse pathResponse = PathResponseAssembler.assemble(loginMember, subwayPath, stations,
                Collections.singletonList(line));

        assertThat(pathResponse.getFare()).isEqualTo(450);
    }
}
