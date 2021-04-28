package com.example.spacex.logic.model


/**
 * @ClassName Rocket
 * @Description TODO
 * @Author mailo
 * @Date 2021/4/28
 */
data class RocketItem(
    val active: Boolean,
    val boosters: Int,
    val company: String,
    val cost_per_launch: Int,
    val country: String,
    val description: String,
    val diameter: Diameter,
    val engines: Engines,
    val first_flight: String,
    val first_stage: FirstStage1,
    val height: Height,
    val id: Int,
    val landing_legs: LandingLegs,
    val mass: Mass,
    val payload_weights: List<PayloadWeight>,
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val second_stage: SecondStage1,
    val stages: Int,
    val success_rate_pct: Int,
    val wikipedia: String
)

data class Diameter(
    val feet: Int,
    val meters: Double
)

data class Engines(
    val engine_loss_max: Int,
    val layout: String,
    val number: Int,
    val propellant_1: String,
    val propellant_2: String,
    val thrust_sea_level: ThrustSeaLevel,
    val thrust_to_weight: Double,
    val thrust_vacuum: ThrustVacuum,
    val type: String,
    val version: String
)

data class FirstStage1(
    val burn_time_sec: Int,
    val engines: Int,
    val fuel_amount_tons: Int,
    val reusable: Boolean,
    val thrust_sea_level: ThrustSeaLevelX,
    val thrust_vacuum: ThrustVacuumX
)

data class Height(
    val feet: Double,
    val meters: Int
)

data class LandingLegs(
    val material: String,
    val number: Int
)

data class Mass(
    val kg: Int,
    val lb: Int
)

data class PayloadWeight(
    val id: String,
    val kg: Int,
    val lb: Int,
    val name: String
)

data class SecondStage1(
    val burn_time_sec: Int,
    val engines: Int,
    val fuel_amount_tons: Int,
    val payloads: Payloads,
    val thrust: Thrust
)

data class ThrustSeaLevel(
    val kN: Int,
    val lbf: Int
)

data class ThrustVacuum(
    val kN: Int,
    val lbf: Int
)

data class ThrustSeaLevelX(
    val kN: Int,
    val lbf: Int
)

data class ThrustVacuumX(
    val kN: Int,
    val lbf: Int
)

data class Payloads(
    val composite_fairing: CompositeFairing,
    val option_1: String,
    val option_2: String
)

data class Thrust(
    val kN: Int,
    val lbf: Int
)

data class CompositeFairing(
    val diameter: DiameterX,
    val height: HeightX
)

data class DiameterX(
    val feet: Double,
    val meters: Double
)

data class HeightX(
    val feet: Int,
    val meters: Double
)