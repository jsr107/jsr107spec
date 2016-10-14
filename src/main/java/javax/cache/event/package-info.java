/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This package contains event listener interfaces.
 * <p>
 * These may be registered for callback notification of the cache events.
 * The specific interface should be implemented for each event type a callback
 * is desired on.
 * <p>
 * Event notifications occur synchronously in the line of execution of the calling thread.
 * The calling thread blocks until the listener has completed execution or thrown a {@link javax.cache.event.CacheEntryCreatedListener}.
 * <p>
 * Listeners are invoked <strong>after</strong> the cache is updated. If the listener throws
 * an {@link javax.cache.event.CacheEntryCreatedListener} this will propagate back to the caller but it does not affect the cache update
 * as it already completed before the listener was called.
 * 
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
package javax.cache.event;
