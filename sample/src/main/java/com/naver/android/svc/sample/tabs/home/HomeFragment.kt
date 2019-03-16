/*
 * Copyright 2018 NAVER Corp.
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
package com.naver.android.svc.sample.tabs.home

import com.naver.android.annotation.OwnSvcFragment
import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireViews
import com.naver.android.svc.sample.tabs.common.CommonViews

/**
 * @author bs.nam@navercorp.com
 */
@OwnSvcFragment
@RequireViews(CommonViews::class)
@RequireControlTower(HomeControlTower::class)
class HomeFragment : SVC_HomeFragment()
