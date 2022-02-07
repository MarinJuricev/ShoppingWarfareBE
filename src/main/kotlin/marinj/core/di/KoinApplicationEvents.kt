package marinj.core.di

import io.ktor.events.EventDefinition
import org.koin.core.KoinApplication

//Taken from https://github.com/InsertKoinIO/koin/pull/1266/files until Koin starts supporting Ktor 2.0

/**
 * Event definition for [KoinApplication] Started event
 */
val KoinApplicationStarted = EventDefinition<KoinApplication>()

/**
 * Event definition for an event that is fired when the [KoinApplication] is going to stop
 */
val KoinApplicationStopPreparing = EventDefinition<KoinApplication>()

/**
 * Event definition for [KoinApplication] Stopping event
 */
val KoinApplicationStopped = EventDefinition<KoinApplication>()