
## Architecture

Three layers:

- `presentation` — Compose screens and ViewModels exposing `StateFlow<UiState<T>>`
- `domain` — models, repository interface, use cases
- `data` — Retrofit service, DTOs, repository implementation

Flow: Screen → ViewModel → UseCase → Repository → ApiService.

## Approach

- Kotlin + Jetpack Compose (Material 3)
- Coroutines and StateFlow for state
- Hilt for dependency injection
- Retrofit + OkHttp, with a fake interceptor serving mock JSON so no backend is required
- Navigation Compose for two screens
- Sealed `UiState` (Loading / Success / Error) drives the UI
- Errors surface in the UI with a retry action

## Assumptions and Limitations

- The API is mocked in-process. Point the base URL at a real server and remove the fake interceptor to use real data.
- No local cache or offline support.
- No UI or instrumentation tests.
- Values from the mock are static per session.

## Submission

GitHub repo: https://github.com/Arunkumarpatil7619/VehicleDashboard-Android
