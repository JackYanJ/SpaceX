package com.example.spacex.logic.model


/**
 * @ClassName Launchs
 * @Description Launchs entity
 * @Author mailo
 * @Date 2021/4/27
 */
class Launches : ArrayList<LaunchItem>()

data class LaunchItem(
    val details: String,
    val flight_number: Int,
    val is_tentative: Boolean,
    val launch_date_local: String,
    val launch_date_unix: Long,
    val launch_date_utc: String,
    val launch_failure_details: LaunchFailureDetails,
    val launch_site: LaunchSite,
    val launch_success: Boolean,
    val launch_window: Int,
    val launch_year: String,
    val links: Links,
    val mission_id: List<Any>,
    val mission_name: String,
    val rocket: Rocket,
    val ships: List<Any>,
    val static_fire_date_unix: Int,
    val static_fire_date_utc: String,
    val tbd: Boolean,
    val telemetry: Telemetry,
    val tentative_max_precision: String,
    val timeline: Timeline,
    val upcoming: Boolean
)

data class LaunchFailureDetails(
    val altitude: Any,
    val reason: String,
    val time: Int
)

data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
)

data class Links(
    val article_link: String,
    val flickr_images: List<Any>,
    val mission_patch: String,
    val mission_patch_small: String,
    val presskit: Any,
    val reddit_campaign: Any,
    val reddit_launch: Any,
    val reddit_media: Any,
    val reddit_recovery: Any,
    val video_link: String,
    val wikipedia: String,
    val youtube_id: String
)

data class Rocket(
    val fairings: Fairings,
    val first_stage: FirstStage,
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val second_stage: SecondStage
)

data class Telemetry(
    val flight_club: Any
)

data class Timeline(
    val webcast_liftoff: Int
)

data class Fairings(
    val recovered: Boolean,
    val recovery_attempt: Boolean,
    val reused: Boolean,
    val ship: Any
)

data class FirstStage(
    val cores: List<Core>
)

data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
)

data class Core(
    val block: Any,
    val core_serial: String,
    val flight: Int,
    val gridfins: Boolean,
    val land_success: Any,
    val landing_intent: Boolean,
    val landing_type: Any,
    val landing_vehicle: Any,
    val legs: Boolean,
    val reused: Boolean
)

data class Payload(
    val customers: List<String>,
    val manufacturer: String,
    val nationality: String,
    val norad_id: List<Any>,
    val orbit: String,
    val orbit_params: OrbitParams,
    val payload_id: String,
    val payload_mass_kg: Int,
    val payload_mass_lbs: Int,
    val payload_type: String,
    val reused: Boolean
)

data class OrbitParams(
    val apoapsis_km: Int,
    val arg_of_pericenter: Any,
    val eccentricity: Any,
    val epoch: Any,
    val inclination_deg: Int,
    val lifespan_years: Any,
    val longitude: Any,
    val mean_anomaly: Any,
    val mean_motion: Any,
    val periapsis_km: Int,
    val period_min: Any,
    val raan: Any,
    val reference_system: String,
    val regime: String,
    val semi_major_axis_km: Any
)