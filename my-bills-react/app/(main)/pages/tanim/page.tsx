'use client';
import React, { useState, useRef } from 'react';
import { Panel } from 'primereact/panel';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Toast } from 'primereact/toast';
import { RadioButton } from 'primereact/radiobutton';
import { InputMask } from 'primereact/inputmask';
import { saveFatura } from '../../services/faturaService';

const FaturaTipTanim = () => {
    const [tanim, setTanim] = useState('');
    const [aciklama, setAciklama] = useState('');
    const [visible, setVisible] = useState(false);
    const toast = useRef<Toast | null>(null);
    const maxChars = 100;
    const [durum, setDurum] = useState(true);
    const [date, setDate] = useState<string>("");

    const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const value = event.target.value;
        if (value.length <= maxChars) {
            setAciklama(value);
        }
    };

    const remainingChars = maxChars - aciklama.length;

    const confirmSave = () => {
        setVisible(true);
    };

    const save = async () => {
        const fatura = {
            name: tanim,
            description: aciklama,
            active: durum,
            needDate: new Date(date).toISOString(),
        };

        console.log("Fatura Nerede: ", fatura);

        try {
            await saveFatura(fatura);  // API çağrısı
            if (toast.current) {
                toast.current.show({ severity: 'success', summary: 'Başarılı', detail: 'Fatura tipi bilgileri kaydedildi!' });
            }
            setVisible(false);
            setTanim('');
            setAciklama('');
            setDurum(true);
            setDate("");
        } catch (error) {
            if (toast.current) {
                toast.current.show({ severity: 'error', summary: 'Hata', detail: error.message });
            }
        }
    };

    const hideDialog = () => {
        setVisible(false);
        if (toast.current) {
            toast.current.show({ severity: 'error', summary: 'İptal', detail: 'Fatura tipi bilgileri iptal edildi!' });
        }
    };

    const handleDateChange = (e: any) => {
        setDate(e.value);
      };

    return (
        <div className="grid">
            <div className="col-12">
                <Panel header="Fatura Tipi Tanımlama" toggleable>
                    <div className="flex flex-column gap-2">
                        <label htmlFor="fatura">Fatura Tanımı</label>
                        <InputText id="fatura" value={tanim} onChange={(e) => setTanim(e.target.value)} placeholder="Fatura Tanımınızı Giriniz." />
                    </div>
                    <br />
                    <div className="flex flex-column gap-2">
                        <label htmlFor="aciklama">Fatura Açıklaması</label>
                        <InputTextarea id="aciklama" rows={5} value={aciklama} onChange={handleChange} placeholder="Fatura Açıklamasını Giriniz." />
                        <div className="text-right">{remainingChars} karakter kaldı</div>
                        {remainingChars <= 0 && <div className="text-red-500">Maksimum karakter sayısına ulaştınız!</div>}
                    </div>
                    <div className='flex flex-column gap-2'>
                        <label htmlFor="date">Son Ödeme Tarihi</label>
                        <InputMask id="date" value={date} onChange={handleDateChange} mask="99/99/9999" placeholder="99/99/9999" slotChar="dd/mm/yyyy"/>
                    </div>
                    <div className="flex flex-row gap-3 mt-3"> {/* Flex row ile yan yana yerleştiriyoruz */}
                        <div>
                            <RadioButton 
                                inputId="kullanımda" 
                                name="durum" 
                                value="kullanımda" 
                                checked={durum === true} 
                                onChange={(e) => setDurum(e.value)} 
                            />
                            <label htmlFor="kullanımda">Kullanımda</label>
                        </div>
                        <div>
                            <RadioButton 
                                inputId="kullanılmıyor" 
                                name="durum" 
                                value="kullanılmıyor" 
                                checked={durum === false} 
                                onChange={(e) => setDurum(e.value)} 
                            />
                            <label htmlFor="kullanılmıyor">Kullanılmıyor</label>
                        </div>
                    </div>
                    <br/><br/>
                    <Button label="Kaydet" icon="pi pi-check" onClick={confirmSave} className="mt-2" />
                    <Dialog header="Kaydet" visible={visible} onHide={hideDialog}>
                        <div>Kaydetmek istediğinize emin misiniz?</div>
                        <div className="flex justify-content-end mt-3">
                            <Button label="İptal" onClick={hideDialog} className="mr-2" />
                            <Button label="Kaydet" onClick={save} icon="pi pi-save" />
                        </div>
                    </Dialog>
                    <Toast ref={toast} />
                </Panel>
            </div>
        </div>
    );
};

export default FaturaTipTanim;
