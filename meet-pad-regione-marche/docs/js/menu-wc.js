'use strict';


customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">meet-pad-regione-marche documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                        <li class="link">
                            <a href="changelog.html"  data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>CHANGELOG
                            </a>
                        </li>
                        <li class="link">
                            <a href="dependencies.html" data-type="chapter-link">
                                <span class="icon ion-ios-list"></span>Dependencies
                            </a>
                        </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse" ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AccreditationModule.html" data-type="entity-link">AccreditationModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AccreditationModule-737ca9c74ba70f2678ae24adbbec0071"' : 'data-target="#xs-components-links-module-AccreditationModule-737ca9c74ba70f2678ae24adbbec0071"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AccreditationModule-737ca9c74ba70f2678ae24adbbec0071"' :
                                            'id="xs-components-links-module-AccreditationModule-737ca9c74ba70f2678ae24adbbec0071"' }>
                                            <li class="link">
                                                <a href="components/AccreditationComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AccreditationComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PendingAccreditationPageComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PendingAccreditationPageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PendingListPageComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PendingListPageComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AccreditationRoutingModule.html" data-type="entity-link">AccreditationRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AccreditationTabModule.html" data-type="entity-link">AccreditationTabModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AccreditationTabModule-4673ea559b3385628b048bacc8004653"' : 'data-target="#xs-components-links-module-AccreditationTabModule-4673ea559b3385628b048bacc8004653"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AccreditationTabModule-4673ea559b3385628b048bacc8004653"' :
                                            'id="xs-components-links-module-AccreditationTabModule-4673ea559b3385628b048bacc8004653"' }>
                                            <li class="link">
                                                <a href="components/AccreditationTabComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AccreditationTabComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AccreditationTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AccreditationTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AccreditationTabWithRoutingModule.html" data-type="entity-link">AccreditationTabWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AdminConsoleModuleLazy.html" data-type="entity-link">AdminConsoleModuleLazy</a>
                            </li>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link">AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-4f29309bbb54996bd9965de48e634219"' : 'data-target="#xs-components-links-module-AppModule-4f29309bbb54996bd9965de48e634219"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-4f29309bbb54996bd9965de48e634219"' :
                                            'id="xs-components-links-module-AppModule-4f29309bbb54996bd9965de48e634219"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AppComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link">AppRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ConferenceModule.html" data-type="entity-link">ConferenceModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' : 'data-target="#xs-components-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' :
                                            'id="xs-components-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' }>
                                            <li class="link">
                                                <a href="components/ConferenceComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ConferenceComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' : 'data-target="#xs-injectables-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' :
                                        'id="xs-injectables-links-module-ConferenceModule-198c0625f00c5b4fb5a09bfe695a1830"' }>
                                        <li class="link">
                                            <a href="injectables/ConferencePermissionsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ConferencePermissionsService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/ConferenceStoreService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ConferenceStoreService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/EventsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>EventsService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/ParticipantsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ParticipantsService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/ConferenceRoutingModule.html" data-type="entity-link">ConferenceRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ConferenceSharedModule.html" data-type="entity-link">ConferenceSharedModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' : 'data-target="#xs-components-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' :
                                            'id="xs-components-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' }>
                                            <li class="link">
                                                <a href="components/AlertInfoComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AlertInfoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConferenceModalBeforeCreateComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ConferenceModalBeforeCreateComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HeadComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">HeadComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PaleoComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PaleoComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#directives-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' : 'data-target="#xs-directives-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' }>
                                        <span class="icon ion-md-code-working"></span>
                                        <span>Directives</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="directives-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' :
                                        'id="xs-directives-links-module-ConferenceSharedModule-8266f144b059609bc07428f70febeea8"' }>
                                        <li class="link">
                                            <a href="directives/AuthConferenceDirective.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuthConferenceDirective</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/CoreModule.html" data-type="entity-link">CoreModule</a>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-CoreModule-31f6354ed4f94b0ae50da86ed500d2cf"' : 'data-target="#xs-injectables-links-module-CoreModule-31f6354ed4f94b0ae50da86ed500d2cf"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-CoreModule-31f6354ed4f94b0ae50da86ed500d2cf"' :
                                        'id="xs-injectables-links-module-CoreModule-31f6354ed4f94b0ae50da86ed500d2cf"' }>
                                        <li class="link">
                                            <a href="injectables/AppPermissionsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>AppPermissionsService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/CustomPreloadingStrategy.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>CustomPreloadingStrategy</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/MenuService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>MenuService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/UserPortalService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>UserPortalService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/UtilityService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>UtilityService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/DashboardModule.html" data-type="entity-link">DashboardModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-DashboardModule-b15aff6b76b7add3c81934ae76cb0749"' : 'data-target="#xs-components-links-module-DashboardModule-b15aff6b76b7add3c81934ae76cb0749"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-DashboardModule-b15aff6b76b7add3c81934ae76cb0749"' :
                                            'id="xs-components-links-module-DashboardModule-b15aff6b76b7add3c81934ae76cb0749"' }>
                                            <li class="link">
                                                <a href="components/MainComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">MainComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/DefinitionModule.html" data-type="entity-link">DefinitionModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-DefinitionModule-09acd875ea4ffdf0509aebc5bb6cd951"' : 'data-target="#xs-components-links-module-DefinitionModule-09acd875ea4ffdf0509aebc5bb6cd951"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-DefinitionModule-09acd875ea4ffdf0509aebc5bb6cd951"' :
                                            'id="xs-components-links-module-DefinitionModule-09acd875ea4ffdf0509aebc5bb6cd951"' }>
                                            <li class="link">
                                                <a href="components/DefinitionComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">DefinitionComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/DefinitionWithRoutingModule.html" data-type="entity-link">DefinitionWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/DesktopModule.html" data-type="entity-link">DesktopModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-DesktopModule-a89e9ee2d6e62b808b07c9019fc46134"' : 'data-target="#xs-components-links-module-DesktopModule-a89e9ee2d6e62b808b07c9019fc46134"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-DesktopModule-a89e9ee2d6e62b808b07c9019fc46134"' :
                                            'id="xs-components-links-module-DesktopModule-a89e9ee2d6e62b808b07c9019fc46134"' }>
                                            <li class="link">
                                                <a href="components/DesktopComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">DesktopComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/DocumentationModule.html" data-type="entity-link">DocumentationModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-DocumentationModule-613c23ae867264569cb8a6e9106a6842"' : 'data-target="#xs-components-links-module-DocumentationModule-613c23ae867264569cb8a6e9106a6842"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-DocumentationModule-613c23ae867264569cb8a6e9106a6842"' :
                                            'id="xs-components-links-module-DocumentationModule-613c23ae867264569cb8a6e9106a6842"' }>
                                            <li class="link">
                                                <a href="components/DocumentationComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">DocumentationComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EditModalFileComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EditModalFileComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FilePanelComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">FilePanelComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FileTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">FileTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/DocumentationWithRoutingModule.html" data-type="entity-link">DocumentationWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/EventsModule.html" data-type="entity-link">EventsModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' : 'data-target="#xs-components-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' :
                                            'id="xs-components-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' }>
                                            <li class="link">
                                                <a href="components/AlertWithLinkComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AlertWithLinkComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EventsComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EventsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EventsSearchComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EventsSearchComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EventsTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EventsTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' : 'data-target="#xs-injectables-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' :
                                        'id="xs-injectables-links-module-EventsModule-4b18f2240631ada533d12339af49bf78"' }>
                                        <li class="link">
                                            <a href="injectables/EventGroupFieldsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>EventGroupFieldsService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/EventStoreService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>EventStoreService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/EventsWithRoutingModule.html" data-type="entity-link">EventsWithRoutingModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-EventsWithRoutingModule-687518305ec80b25b4fa49e5ab1783bb"' : 'data-target="#xs-components-links-module-EventsWithRoutingModule-687518305ec80b25b4fa49e5ab1783bb"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-EventsWithRoutingModule-687518305ec80b25b4fa49e5ab1783bb"' :
                                            'id="xs-components-links-module-EventsWithRoutingModule-687518305ec80b25b4fa49e5ab1783bb"' }>
                                            <li class="link">
                                                <a href="components/EventsComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EventsComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/MainModule.html" data-type="entity-link">MainModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-MainModule-871677196ab9573bff2ba3eb41433bee"' : 'data-target="#xs-components-links-module-MainModule-871677196ab9573bff2ba3eb41433bee"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-MainModule-871677196ab9573bff2ba3eb41433bee"' :
                                            'id="xs-components-links-module-MainModule-871677196ab9573bff2ba3eb41433bee"' }>
                                            <li class="link">
                                                <a href="components/MainComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">MainComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/MainRoutingModule.html" data-type="entity-link">MainRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/MediaLibraryModule.html" data-type="entity-link">MediaLibraryModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' : 'data-target="#xs-components-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' :
                                            'id="xs-components-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' }>
                                            <li class="link">
                                                <a href="components/MediaMainComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">MediaMainComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MediaSearchComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">MediaSearchComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MediaTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">MediaTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' : 'data-target="#xs-injectables-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' :
                                        'id="xs-injectables-links-module-MediaLibraryModule-cca5918f213637d6eb7036773a54f7e7"' }>
                                        <li class="link">
                                            <a href="injectables/MediaLibraryService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>MediaLibraryService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/MediaRoutingModule.html" data-type="entity-link">MediaRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ParticipantsModule.html" data-type="entity-link">ParticipantsModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ParticipantsModule-a7fcd72f5b6884959593d5a40fa1e134"' : 'data-target="#xs-components-links-module-ParticipantsModule-a7fcd72f5b6884959593d5a40fa1e134"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ParticipantsModule-a7fcd72f5b6884959593d5a40fa1e134"' :
                                            'id="xs-components-links-module-ParticipantsModule-a7fcd72f5b6884959593d5a40fa1e134"' }>
                                            <li class="link">
                                                <a href="components/ParticipantsComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ParticipantsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ParticipantsTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ParticipantsTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/ParticipantsWithRoutingModule.html" data-type="entity-link">ParticipantsWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/PecModule.html" data-type="entity-link">PecModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-PecModule-63da767d6be0718f801f97fff3501df1"' : 'data-target="#xs-components-links-module-PecModule-63da767d6be0718f801f97fff3501df1"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-PecModule-63da767d6be0718f801f97fff3501df1"' :
                                            'id="xs-components-links-module-PecModule-63da767d6be0718f801f97fff3501df1"' }>
                                            <li class="link">
                                                <a href="components/PecComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PecComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PecSearchComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PecSearchComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/PecWithRoutingModule.html" data-type="entity-link">PecWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/PrivateModule.html" data-type="entity-link">PrivateModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' : 'data-target="#xs-components-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' :
                                            'id="xs-components-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' }>
                                            <li class="link">
                                                <a href="components/CalendarComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">CalendarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NotAuthorizedComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">NotAuthorizedComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NothingToDoComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">NothingToDoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PrivateComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PrivateComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/RelevantInformationComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">RelevantInformationComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' : 'data-target="#xs-injectables-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' :
                                        'id="xs-injectables-links-module-PrivateModule-f5bcc1754acb67f06a54874f1baa24ce"' }>
                                        <li class="link">
                                            <a href="injectables/AccreditationService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>AccreditationService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/CalendarService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>CalendarService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/ConferenceService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ConferenceService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/PrivateRoutingModule.html" data-type="entity-link">PrivateRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ProcedureModule.html" data-type="entity-link">ProcedureModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ProcedureModule-a41975ad3a4ff6c97058200a211e76c3"' : 'data-target="#xs-components-links-module-ProcedureModule-a41975ad3a4ff6c97058200a211e76c3"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ProcedureModule-a41975ad3a4ff6c97058200a211e76c3"' :
                                            'id="xs-components-links-module-ProcedureModule-a41975ad3a4ff6c97058200a211e76c3"' }>
                                            <li class="link">
                                                <a href="components/ProcedureComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ProcedureComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/ProcedureWithRoutingModule.html" data-type="entity-link">ProcedureWithRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/PublicModule.html" data-type="entity-link">PublicModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-PublicModule-1812258887ba109ac00a8175c57b289f"' : 'data-target="#xs-components-links-module-PublicModule-1812258887ba109ac00a8175c57b289f"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-PublicModule-1812258887ba109ac00a8175c57b289f"' :
                                            'id="xs-components-links-module-PublicModule-1812258887ba109ac00a8175c57b289f"' }>
                                            <li class="link">
                                                <a href="components/PublicComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PublicComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/PublicRoutingModule.html" data-type="entity-link">PublicRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/SearchModule.html" data-type="entity-link">SearchModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' : 'data-target="#xs-components-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' :
                                            'id="xs-components-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' }>
                                            <li class="link">
                                                <a href="components/SearchComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">SearchComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' : 'data-target="#xs-injectables-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' :
                                        'id="xs-injectables-links-module-SearchModule-a13d86e681181cc85a14e48a88442992"' }>
                                        <li class="link">
                                            <a href="injectables/ConferenceService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ConferenceService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/SharedModule.html" data-type="entity-link">SharedModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' : 'data-target="#xs-components-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' :
                                            'id="xs-components-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' }>
                                            <li class="link">
                                                <a href="components/ContactComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ContactComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#directives-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' : 'data-target="#xs-directives-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' }>
                                        <span class="icon ion-md-code-working"></span>
                                        <span>Directives</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="directives-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' :
                                        'id="xs-directives-links-module-SharedModule-61b5b69ebc805559213482c4cf9bd506"' }>
                                        <li class="link">
                                            <a href="directives/AuthDirective.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuthDirective</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/SharedPrivateModule.html" data-type="entity-link">SharedPrivateModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-SharedPrivateModule-3637c647f229fdad1277e4f7f1febe98"' : 'data-target="#xs-components-links-module-SharedPrivateModule-3637c647f229fdad1277e4f7f1febe98"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-SharedPrivateModule-3637c647f229fdad1277e4f7f1febe98"' :
                                            'id="xs-components-links-module-SharedPrivateModule-3637c647f229fdad1277e4f7f1febe98"' }>
                                            <li class="link">
                                                <a href="components/ResultTableComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ResultTableComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/SummaryModule.html" data-type="entity-link">SummaryModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-SummaryModule-e0c7929605cc8ce30add0c20a0d17c1b"' : 'data-target="#xs-components-links-module-SummaryModule-e0c7929605cc8ce30add0c20a0d17c1b"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-SummaryModule-e0c7929605cc8ce30add0c20a0d17c1b"' :
                                            'id="xs-components-links-module-SummaryModule-e0c7929605cc8ce30add0c20a0d17c1b"' }>
                                            <li class="link">
                                                <a href="components/SummaryComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">SummaryComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#components-links"' :
                            'data-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Components</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/AccreditationComponent.html" data-type="entity-link">AccreditationComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/AlertInfoComponent.html" data-type="entity-link">AlertInfoComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/AlertWithLinkComponent.html" data-type="entity-link">AlertWithLinkComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ConferenceModalBeforeCreateComponent.html" data-type="entity-link">ConferenceModalBeforeCreateComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DesktopComponent.html" data-type="entity-link">DesktopComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/EditModalFileComponent.html" data-type="entity-link">EditModalFileComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/EventsTableComponent.html" data-type="entity-link">EventsTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FilePanelComponent.html" data-type="entity-link">FilePanelComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FileTableComponent.html" data-type="entity-link">FileTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/HeadComponent.html" data-type="entity-link">HeadComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/MainComponent-1.html" data-type="entity-link">MainComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PaleoComponent.html" data-type="entity-link">PaleoComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ParticipantsTableComponent.html" data-type="entity-link">ParticipantsTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SearchComponent-1.html" data-type="entity-link">SearchComponent</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#directives-links"' :
                                'data-target="#xs-directives-links"' }>
                                <span class="icon ion-md-code-working"></span>
                                <span>Directives</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse" ${ isNormalMode ? 'id="directives-links"' : 'id="xs-directives-links"' }>
                                <li class="link">
                                    <a href="directives/AuthDirective.html" data-type="entity-link">AuthDirective</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#classes-links"' :
                            'data-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse" ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/AccreditamentInfo.html" data-type="entity-link">AccreditamentInfo</a>
                            </li>
                            <li class="link">
                                <a href="classes/AccreditamentPreview.html" data-type="entity-link">AccreditamentPreview</a>
                            </li>
                            <li class="link">
                                <a href="classes/AccreditationFile.html" data-type="entity-link">AccreditationFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/AdditionalFile.html" data-type="entity-link">AdditionalFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/Applicant.html" data-type="entity-link">Applicant</a>
                            </li>
                            <li class="link">
                                <a href="classes/BaseAuthDirective.html" data-type="entity-link">BaseAuthDirective</a>
                            </li>
                            <li class="link">
                                <a href="classes/Calendar.html" data-type="entity-link">Calendar</a>
                            </li>
                            <li class="link">
                                <a href="classes/Company.html" data-type="entity-link">Company</a>
                            </li>
                            <li class="link">
                                <a href="classes/Conference.html" data-type="entity-link">Conference</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceClosing.html" data-type="entity-link">ConferenceClosing</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceCreated.html" data-type="entity-link">ConferenceCreated</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceIndiction.html" data-type="entity-link">ConferenceIndiction</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceMemo.html" data-type="entity-link">ConferenceMemo</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferencePermissions.html" data-type="entity-link">ConferencePermissions</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferencePreliminary.html" data-type="entity-link">ConferencePreliminary</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceStep.html" data-type="entity-link">ConferenceStep</a>
                            </li>
                            <li class="link">
                                <a href="classes/ConferenceTemplate.html" data-type="entity-link">ConferenceTemplate</a>
                            </li>
                            <li class="link">
                                <a href="classes/DateRule.html" data-type="entity-link">DateRule</a>
                            </li>
                            <li class="link">
                                <a href="classes/Definition.html" data-type="entity-link">Definition</a>
                            </li>
                            <li class="link">
                                <a href="classes/DefinitionTemplate.html" data-type="entity-link">DefinitionTemplate</a>
                            </li>
                            <li class="link">
                                <a href="classes/Determination.html" data-type="entity-link">Determination</a>
                            </li>
                            <li class="link">
                                <a href="classes/Documentation.html" data-type="entity-link">Documentation</a>
                            </li>
                            <li class="link">
                                <a href="classes/Event.html" data-type="entity-link">Event</a>
                            </li>
                            <li class="link">
                                <a href="classes/FinalResolution.html" data-type="entity-link">FinalResolution</a>
                            </li>
                            <li class="link">
                                <a href="classes/Folder.html" data-type="entity-link">Folder</a>
                            </li>
                            <li class="link">
                                <a href="classes/FormStep.html" data-type="entity-link">FormStep</a>
                            </li>
                            <li class="link">
                                <a href="classes/FormStep-1.html" data-type="entity-link">FormStep</a>
                            </li>
                            <li class="link">
                                <a href="classes/GenericCommunication.html" data-type="entity-link">GenericCommunication</a>
                            </li>
                            <li class="link">
                                <a href="classes/IndictionFile.html" data-type="entity-link">IndictionFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/Instance.html" data-type="entity-link">Instance</a>
                            </li>
                            <li class="link">
                                <a href="classes/InstanceTemplate.html" data-type="entity-link">InstanceTemplate</a>
                            </li>
                            <li class="link">
                                <a href="classes/IntegrationClosed.html" data-type="entity-link">IntegrationClosed</a>
                            </li>
                            <li class="link">
                                <a href="classes/IntegrationOnlyOneRequest.html" data-type="entity-link">IntegrationOnlyOneRequest</a>
                            </li>
                            <li class="link">
                                <a href="classes/IntegrationRegistered.html" data-type="entity-link">IntegrationRegistered</a>
                            </li>
                            <li class="link">
                                <a href="classes/IntegrationRequest.html" data-type="entity-link">IntegrationRequest</a>
                            </li>
                            <li class="link">
                                <a href="classes/IntegrationSend.html" data-type="entity-link">IntegrationSend</a>
                            </li>
                            <li class="link">
                                <a href="classes/InterectionFile.html" data-type="entity-link">InterectionFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/Localization.html" data-type="entity-link">Localization</a>
                            </li>
                            <li class="link">
                                <a href="classes/MediaLibrary.html" data-type="entity-link">MediaLibrary</a>
                            </li>
                            <li class="link">
                                <a href="classes/MeetAddress.html" data-type="entity-link">MeetAddress</a>
                            </li>
                            <li class="link">
                                <a href="classes/OpinionExpress.html" data-type="entity-link">OpinionExpress</a>
                            </li>
                            <li class="link">
                                <a href="classes/PaleoConference.html" data-type="entity-link">PaleoConference</a>
                            </li>
                            <li class="link">
                                <a href="classes/PaleoFile.html" data-type="entity-link">PaleoFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/Participant.html" data-type="entity-link">Participant</a>
                            </li>
                            <li class="link">
                                <a href="classes/ParticipantTemplate.html" data-type="entity-link">ParticipantTemplate</a>
                            </li>
                            <li class="link">
                                <a href="classes/Pec.html" data-type="entity-link">Pec</a>
                            </li>
                            <li class="link">
                                <a href="classes/PreliminaryFile.html" data-type="entity-link">PreliminaryFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/Procedure.html" data-type="entity-link">Procedure</a>
                            </li>
                            <li class="link">
                                <a href="classes/ProcedureTemplate.html" data-type="entity-link">ProcedureTemplate</a>
                            </li>
                            <li class="link">
                                <a href="classes/QuerySearchEvents.html" data-type="entity-link">QuerySearchEvents</a>
                            </li>
                            <li class="link">
                                <a href="classes/SearchAdvancedCriteria.html" data-type="entity-link">SearchAdvancedCriteria</a>
                            </li>
                            <li class="link">
                                <a href="classes/SearchConference.html" data-type="entity-link">SearchConference</a>
                            </li>
                            <li class="link">
                                <a href="classes/SharedFile.html" data-type="entity-link">SharedFile</a>
                            </li>
                            <li class="link">
                                <a href="classes/SupportContact.html" data-type="entity-link">SupportContact</a>
                            </li>
                            <li class="link">
                                <a href="classes/User.html" data-type="entity-link">User</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AppPermissionsService.html" data-type="entity-link">AppPermissionsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ConferencePermissionsService.html" data-type="entity-link">ConferencePermissionsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ConferenceService.html" data-type="entity-link">ConferenceService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ConferenceStoreService.html" data-type="entity-link">ConferenceStoreService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/CustomPreloadingStrategy.html" data-type="entity-link">CustomPreloadingStrategy</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DocumentsService.html" data-type="entity-link">DocumentsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/EventGroupFieldsService.html" data-type="entity-link">EventGroupFieldsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/EventsService.html" data-type="entity-link">EventsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/EventStoreService.html" data-type="entity-link">EventStoreService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/MenuService.html" data-type="entity-link">MenuService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ParticipantsService.html" data-type="entity-link">ParticipantsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/PecService.html" data-type="entity-link">PecService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SupportContactService.html" data-type="entity-link">SupportContactService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserPortalService.html" data-type="entity-link">UserPortalService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UtilityService.html" data-type="entity-link">UtilityService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#guards-links"' :
                            'data-target="#xs-guards-links"' }>
                            <span class="icon ion-ios-lock"></span>
                            <span>Guards</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse" ${ isNormalMode ? 'id="guards-links"' : 'id="xs-guards-links"' }>
                            <li class="link">
                                <a href="guards/AccreditationResolver.html" data-type="entity-link">AccreditationResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/AuthAdminGuard.html" data-type="entity-link">AuthAdminGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/CalendarResolver.html" data-type="entity-link">CalendarResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceBreadcrumbResolver.html" data-type="entity-link">ConferenceBreadcrumbResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceContactsResolver.html" data-type="entity-link">ConferenceContactsResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceCreateGuard.html" data-type="entity-link">ConferenceCreateGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceDetailGuard.html" data-type="entity-link">ConferenceDetailGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceDocumentsResolver.html" data-type="entity-link">ConferenceDocumentsResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceParticipantsResolver.html" data-type="entity-link">ConferenceParticipantsResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceResolver.html" data-type="entity-link">ConferenceResolver</a>
                            </li>
                            <li class="link">
                                <a href="guards/ConferenceStepGuard.html" data-type="entity-link">ConferenceStepGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/JoinGuard.html" data-type="entity-link">JoinGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/PendingAccreditationGuard.html" data-type="entity-link">PendingAccreditationGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/PrivateGuard.html" data-type="entity-link">PrivateGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/PublicGuard.html" data-type="entity-link">PublicGuard</a>
                            </li>
                            <li class="link">
                                <a href="guards/UserGuard.html" data-type="entity-link">UserGuard</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse" ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/AuthParams.html" data-type="entity-link">AuthParams</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CompanySection.html" data-type="entity-link">CompanySection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ConferenceTemplateToApply.html" data-type="entity-link">ConferenceTemplateToApply</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/GetFileReturn.html" data-type="entity-link">GetFileReturn</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse" ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/functions.html" data-type="entity-link">Functions</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/typealiases.html" data-type="entity-link">Type aliases</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise-inverted.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});