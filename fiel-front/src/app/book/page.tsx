'use server';
import BookComponent from '@/components/bookComponent';

type PageProps = {
    searchParams: Promise<{ [key: string]: string | string[] | undefined }>;
}

export default async function BookPage({ searchParams }: PageProps) {
    const sp = await searchParams;
    
    const raw = sp.bookId ?? sp.bookid;
    const bookIdParam = Array.isArray(raw) ? raw[0] : raw;

    const bookId =
        typeof bookIdParam === 'string' && !Number.isNaN(Number(bookIdParam))
        ? Number(bookIdParam)
        : null;

    return <BookComponent bookId={bookId} />;
}
