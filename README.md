# Monadnock — historical archive

Unfinished Android client with a Java/Volley receipt integration and a parallel Kotlin architecture experiment.

A personal receipt and food-information project developed in 2017–2019. Heartleaf supplied the web interface and data ingestion; TwinPeaks supplied the Java backend; Monadnock explored Android access. These repositories preserve that work as historical source, with publication cleanup performed in 2026. They are not maintained or presented as production-ready software.

## Related repositories

[Heartleaf](https://github.com/dpkimball/heartleaf-archive) · [Twinpeaks](https://github.com/dpkimball/twinpeaks-archive) · [Monadnock](https://github.com/dpkimball/monadnock-archive)

## Publication changes

Original history is excluded. AOSP sample headers are retained with the complete Apache 2.0 license and explicit attribution. Small publication repairs normalize bearer headers, handle empty responses, recognize signup HTTP 201, and bound login waiting. The app remains unfinished; it has not been converted into a complete receipt client.

## Historical environment

Historical Android/Gradle setup is retained in the build files. The Volley service points at emulator-local http://10.0.2.2:8090. The original JWT backend contract is documented by the source but is no longer provided by the cleaned TwinPeaks archive. A JDK and compatible Android SDK are needed to attempt a build.

## Limitations

No Android build or emulator execution was performed. Dashboard layout inflation is commented out; login opens sample-derived MainActivity. Kotlin Room/Retrofit/Dagger/ViewModel code still uses GitHub endpoints/models and must not be presented as a finished receipt architecture. Many tests exercise the sample rather than the receipt integration.

## License and attribution

Original contributions and publication replacements are MIT licensed under LICENSE. Third-party portions retain their own notices and terms; see THIRD_PARTY.md. No ownership of upstream sample code is claimed.
