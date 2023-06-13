# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Known knowns
- PLACEHOLDER REMOVE IF EMPTY: risks that were introduced or discovered in the release and are known but not resolved

## [2.7.0]
### Added
- Handling of Decentral Digital Twin Registry as a way of request AAS for identifier
  - Extend Register Job with key field that contain BPN and globalAssetId
  - Requesting BPN endpoint catalog over Discrovery Finder
  - Requesting EDC endpoint addresses for BPN over EDC Discovery Finder
  - Add filter for catalog item search in EDC
  - Authorize Digital Twin client with EDC Endpoint Reference

## [2.6.1] - 2023-05-15
### Added
- Validation if bpnEndpoint is set in properties before starting a job with lookupBPNs set to true
- Automate release workflow
- Validate if callback url starts with http or https before register a job

## [2.6.0] - 2023-05-05
### Added
- IRS now checks the EDC policies and only negotiates contracts if the policy matches the ones defined in the configuration at `edc.catalog.policies.allowedNames` (comma separated string)

### Changed
- Restructured the repository to make it more comprehensive
- Improved API descriptions regarding errors 

## [2.5.1] - 2023-04-28
### Changed
- Replaced Discovery Service mock with real implementation

## [2.5.0] - 2023-04-17
### Added
- Introduced Batch processing API endpoints. Batch Order is registered and executed for a bunch of globalAssetIds in one call.
  - API Endpoint POST Register Batch Order {{IRS_HOST}}/irs/orders
  - API Endpoint GET Batch Order {{IRS_HOST}}/irs/orders/:orderId
  - API Endpoint GET Batch {{IRS_HOST}}/irs/orders/:orderId/batches/:batchId
- Introduced Environmental- and Social Standards processing API endpoints. 
  - API Endpoint POST Register job to start an investigation if a given bpn is contained in a part chain {{IRS_HOST}}/ess/bpn/investigations
  - API Endpoint GET BPN Investigation {{IRS_HOST}}/ess/bpn/investigations/:id
  - API Endpoint POST EDC Notification receive {{IRS_HOST}}/ess/notification/receive


## [2.4.1] - 2023-04-21
### Fixed
- Updated spring-boot version to 3.0.6 to fix security issue
- change GID in Dockerfile to fix https://github.com/eclipse-tractusx/item-relationship-service/issues/101


## [2.4.0] - 2023-03-30
### Added
- IRS is now able to cache the EDC catalog. Caching can be disabled via application config. Maximum amount of cached items and item time-to-live can be configured as well. 

### Changed
- API endpoints have now additional layer of security and require BPN claim in token. Allowed BPN that can access API can be configured with (*env:API_ALLOWED_BPN*) variable.
- Updated Spring Boot dependency to 3.0.5

### Fixed
- Fixed issue in paging when calling SemanticsHub with some page size configurations 


## [2.3.2] - 2023-03-20
### Changed
- Replace pandoc with downdoc for conversion asciidoc to markdown

### Fixed
- In AssemblyPartRelationship the ``measurementUnit`` can be both parsed from both string and object versions
- Decode URLs for ``assetId`` to prevent bug that encoded ``assetId`` cannot be found in the catalog

## [2.3.1] - 2023-03-07
### Changed
- Updated Spring Boot dependency to 3.0.3

## [2.3.0] - 2023-02-21
### Added
- Introduced new endpoint ``/irs/aspectmodels`` which will list all available aspect models (from semantic hub or locally provided files if present)

### Fixed
- If Grafana is enabled - dashboards will be automatically imported on startup

### Changed
- Job creation validates ``aspects`` by using models available in semantic hub or locally provided.

## [2.2.1] - 2023-03-15
### Fixed
- Property "measurementUnit" of AssemblyPartRelationship can now be a String or a Map. According to the latest model, it is supposed to be a String, but due to varying test data, IRS supports both variants.
- EDC Catalog IDs are now being URL decoded before usage

## [2.2.0] - 2023-01-20
### Added
- Added new job parameter flag "lookupBPNs" which toggles lookup of BPN numbers using the configured BPN URL
- Added new summary item "bpnLookups" which tracks completed and failed BPN requests. Excluded these metrics from "asyncFetchedItems"
- Model schema JSON files can now be provided locally as a backup to the Semantic Hub.
  Use the new ``semanticsHub.localModelDirectory`` config entry to provide a folder with the models.
- Added pagination to EDC catalog retrieval.

### Fixed
- BPNs array is now filled correctly when requesting a running job with parameter "returnUncompletedJob=true"

## [2.1.0] - 2023-01-11
### Changed
- Change 'jobParameter' to 'parameter' in GET calls in IRS API
- Change 'jobStates' to 'states' request parameter in GET call for jobs by states, 'jobStates' is now deprecated
- REST clients for DTR, SemHub and BPDM now use their own RestTemplates and configuration
- application.yaml received some documentation

## [2.0.0] - 2022-12-09
### Added
- Added pagination to GET /irs/jobs endpoint (eg. {{IRS_HOST}}/irs/jobs?page=0&size=10&sort=completedOn,asc)

### Changed
- IRS API now requires 'view_irs' resource access inside Keycloak JWT token.
- New 2.0.0 version of IRS API. Main goal was to remove 'job' prefix from attribute names
  - change 'jobId' to 'id' in GET and POST calls
  - change 'jobState' to 'state' in GET calls
  - change 'jobCompleted' to 'completedOn' in GET calls
  - change 'jobId' to 'id' and 'jobState' to 'state' in callback URI variables

## [1.6.0] - 2022-11-25
### Added
- EDC client implementation (for negotiation and data exchange)
- New callback endpoint for EDC (path: /internal/endpoint-data-reference)
- Optional trusted port to make internal interfaces only available via that (config: server.trustedPort)

### Removed
- Removed the need for the API wrapper by directly communicating with the EDC control and data plane.

## [1.5.0] - 2022-11-11
### Added
- Added new parameters 'startedOn' and 'jobCompleted' to Job status response

### Changed
- Updated Spring Boot to 2.7.5 and Spring Security (Web and OAuth2 Client) dependencies to 5.7.5 due to CVEs
- Renamed parameter from 'status' to 'jobState' in Job status response
- Time to live for finished jobs is now configurable

## [1.4.0] - 2022-10-28
### Added
- Added new 'asPlanned' value for bomLifecycle request parameter - now BomAsPlanned can be traversed by the IRS to build relationships

## [1.3.0] - 2022-10-18
### Added
- BPDM URL (*env:BPDM_URL*) is now configurable
- SemanticsHub URL (*env:SEMANTICSHUB_URL*) and default URNs (*env:SEMANTICSHUB_DEFAULT_URNS*) are now configurable
- Added an administration guide covering installation and configuration topics (TRI-593)
- **Tombstones** Tombstone contains ProcessStep in ProcessingError
- Added new optional parameter 'callbackUrl' to Job registration request

### Known knowns
- discovered lack of circuit breaker for communication with submodel server which is not responding (low risk) - will be addressed in future release

## [1.2.0] - 2022-09-30
### Added
- Automatic eclipse dash IP-ticket creation
- Automatic cucumber execution based on Tests in Jira

### Fixed
- Update HSTS header configuration (TRI-659)
- Encode log output to avoid log forging (TRI-653)
- Add missing X-Frame-Options header (TRI-661)
- Switching to a distroless Docker base image to avoid vulnerable library (TRI-729)

### Changed
- Update EDC components to version 0.1.1
- Update testdata set to 1.3.2
- Create Tombstone for faulty/null/none BPN ManufactureId
- Update aaswrapper to 0.0.7

## [1.1.0] - 2022-09-12
### Added
- **Aspect Model validation** IRS now validates the aspect model responses requested via EDC. JSON schema files are requested on demand using Semantic Hub API.
- **BPN mapping** IRS job result includes BPNs and the corresponding names.
- **Enabled collecting of "Batch" submodels** IRS supports aspect model "Batch"

### Fixed
- **Malformed date-time in IRS job result** (TRI-627)
- **Job cleanup process** Jobs are completely deleted after retention period exceeded. (TRI-631)
- **IRS job processing** IRS jobs no longer stay stuck in state RUNNING due to malformed URLs. (TRI-675)
- **Security fixes** Fixed various security findings.

### Changed
- **IRS monitoring** Added more metrics and improved Grafana dashboards.
- **Submodel payload in IRS job response** Submodels are stored as object instead of string.
- **CORS** Enabled CORS configuration
- **Documentation** Improved README and UML diagrams.
- **GitHub integrations** Trivy/KICS/Eclipse DASH tool/VeraCode
- **Extended Postman collection**
- **IRS stability and code quality**
- **API docs**
- **Test data and upload script**
- **Helm charts** Improved security and performance configurations. Created a All-in-One Helm Chart for IRS which includes all IRS dependencies. Helm Chart is released separately.
- **Refactor relationship result object of IRS**
- **FOSS initial GitHub & code preparation** Change package structure to `org.eclipse.tractusx`.

## [1.0.0] - 2022-07-25
### Changed
* **Improved Minio Helmchart** Latest Minio version is used now
* **Submodel Information** If requested, the IRS collects submodel information now and adds it to the job result
* **Improved job response** The job response object contains all the required fields now with correct values

## [0.9.1] - 2022-06-14
### Removed
- **Remove AAS Proxy** The IRS works without the AASProxy component

## [0.9.0] - 2022-04-27
### Added
- **Build traceability BoM as built tree** You can now use the IRS to retrieve a BoM tree with lifecycle stage "as built" for serialized components, which are distributed across the Catena-X network. In this release, the tree is being built on the aspects "SerialPartTypization" and "AssemblyPartRelationship". Focus is a tree built  in the direction top-down/parent-child.
- *IRS API v1.0.0* First release of the IRS API.

### Fixed
- **Cloud Agnostic Solution** You have the ability now to deploy the solution on different cloud vendor solutions. We decoupled the application from its former Azure Stack.
- **Security fixes** Various security fixes.

### Changed
- **Asynchronous Job Management** Since we cannot rely on a synchronous answer of each request  within the network, we provide a job management for this process.
- **AAS Proxy**  Requests to Digital Twin Registry are executed via the AAS Proxy.
- **Quality Gate for Release 1** The quality measures were implemented in accordance with the requirements for Release 1.
- **Hotel Budapest catenax-ng** C-X-NG ready using the provided catenax-ng infrastructure.
- **SCA Composition Analysis** Enablement of SCA Composition Analysis using Veracode and CodeQl.
- **Github Integrations** VeraCode/Dependabot/SonarCloud/CodeQl

### Unresolved
- **Select Aspects you need**  You are able to select the needed aspects for which you want to collect the correct endpoint information.

[Unreleased]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.6.1...HEAD
[2.6.1]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.6.0...2.6.1
[2.6.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.5.1...2.6.0
[2.5.1]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.5.0...2.5.1
[2.5.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.4.0...2.5.0
[2.4.1]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.4.0...2.4.1
[2.4.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.3.2...2.4.0
[2.3.2]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.3.1...2.3.2
[2.3.1]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.3.0...2.3.1
[2.3.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.2.0...2.3.0
[2.2.1]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.2.0...2.2.1
[2.2.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.1.0...2.2.0
[2.1.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/2.0.0...2.1.0
[2.0.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/1.6.0...2.0.0
[1.6.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/1.5.0...1.6.0
[1.5.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/1.4.0...1.5.0
[1.4.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/1.3.0...1.4.0
[1.3.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/1.2.0...1.3.0
[1.2.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/v1.1.0...1.2.0
[1.1.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/eclipse-tractusx/item-relationship-service/compare/v0.9.1...v1.0.0
[0.9.1]: https://github.com/eclipse-tractusx/item-relationship-service/commits/v0.9.1
[0.9.0]: https://github.com/eclipse-tractusx/item-relationship-service/commits/v0.9.0