package wooteco.subway.maps.map.dto;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathResponseAssembler {
    private static final int STANDARD_FARE = 1_250;

    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
                .map(it -> StationResponse.of(stations.get(it)))
                .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();
        int fare = calculateFare(distance);

        return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, fare);
    }

    private static int calculateFare(int distance) {
        if (distance <= 10) {
            return STANDARD_FARE;
        }
        if (distance <= 50) {
            return ((((distance - 10) / 5) + 1) * 100) + STANDARD_FARE;
        }
        return ((((distance - 50) / 8) + 1) * 100) + 1000 + STANDARD_FARE;
    }
}
