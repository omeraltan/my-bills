/* eslint-disable @next/next/no-img-element */

import React, { useContext } from 'react';
import AppMenuitem from './AppMenuitem';
import { LayoutContext } from './context/layoutcontext';
import { MenuProvider } from './context/menucontext';
import Link from 'next/link';
import { AppMenuItem } from '@/types';

const AppMenu = () => {
    const { layoutConfig } = useContext(LayoutContext);

    const model: AppMenuItem[] = [
        {
            label: 'Ana Sayfa',
            items: [{ label: 'Dashboard', icon: 'pi pi-fw pi-home', to: '/' }]
        },
        {
            label: 'Tanım',
            items: [{ label: 'Fatura Tip', icon: 'pi pi-fw pi-book', to: '/pages/tanim' }]
        },
        {
            label: 'Faturalar',
            items: [{ label: 'Faturalar', icon: 'pi pi-fw pi-money-bill', to: '/pages/faturalar' }]
        },
        {
            label: 'Rapor',
            icon: 'pi pi-fw pi-pi-info',
            to:'pages',
            items:[
                {
                    label: 'Fatura Detay',
                    icon:'pi pi-fw pi-briefcase',
                    to: '/landing'
                },
                {
                    label: 'Analiz',
                    icon:'pi pi-fw pi-briefcase',
                    to: '/landing'
                },
                {
                    label: 'Rapor',
                    icon:'pi pi-fw pi-briefcase',
                    to: '/landing'
                }
            ]
            
        }
    ];

    return (
        <MenuProvider>
            <ul className="layout-menu">
                <Link href="/" target="_blank" style={{ cursor: 'pointer' }}>
                    <img alt="Fatura Analiz" className="w-full mt-3" src={`/layout/images/banner-faturaanaliz-colorful${layoutConfig.colorScheme === 'light' ? '' : '-dark'}.webp`} />
                </Link>
                {model.map((item, i) => {
                    return !item?.seperator ? <AppMenuitem item={item} root={true} index={i} key={item.label} /> : <li className="menu-separator"></li>;
                })}

                
            </ul>
        </MenuProvider>
    );
};

export default AppMenu;
